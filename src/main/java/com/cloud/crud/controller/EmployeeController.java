package com.cloud.crud.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.crud.bean.Employee;
import com.cloud.crud.bean.Msg;
import com.cloud.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 处理员工的 CRUD 请求
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 解决方案:
	 * 我们要是能支持直接发送PUT之类的请求还要封装请求体中的数据
	 * 配置上HttpPutFormContentFilter
	 * 它的作用：将请求体中的数据包装成一个Map
	 * 		request被重新包装 request.getParameter()被重写,就会从自己封装的map中取值
	 * 员工更新方法 
	 * 如果直接发送 PUT 请求
	 * 封装的数据
	 * 将要更新的员工数据Employee [empId=1025, empName=null, gender=null, email=null, dId=null, department=null]
	 * 
	 * 问题: 请求体中有数据,但是更新不上,无法封装Employee
	 * 原因: 
	 * 		Tomcat:
	 * 			1、将请求体中的数据封装到Map,封装到Map
	 * 			2、request.getParameter("empName")就会从map中取值
	 * 			3、SpringMVC封装POJO对象的时候,
	 * 					会把POJO中的每个属性值,从这个Map中用request.getParameter("empName")获取
	 * 	AJAX 发送put请求引发的血案
	 * 		PUT请求，请求体中的数据,request.getParameter("empName")拿不到数据
	 * 		Tomcat一看是PUT请求，就不会封装Map，只有是POST请求的时候才会封装请求体为Map
	 * org.apache.catalina.connector.Request -- parseParameters()
	 * 
	 * protected String parseBodyMethods = "POST";
	 * if( !getConnnector().isParseBodyMethod(getMethod()) ){
	 * 				success = true;
	 * 				return;
	 * 		}
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee) {
		System.out.println("将要更新的员工数据" + employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	//查询对应的id 员工
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		
		Employee employee = employeeService.getEmp(id);
		
		return Msg.success().add("emp", employee);
	}
	
	
	/**
	 * 检查用户名是否可用
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public Msg checkUser(@RequestParam("empName")String empName) {
		//先判断用户名是否合法
		String regex = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regex)) {
			return Msg.fail().add("va_msg", "用户名必须是4-16位数字和2-5位中文字符");
		}
		
		boolean b = employeeService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	/**
	 * 1、员工校验 
	 *  1、支持JSR303
	 *  2、导入 Hibernate-Validator
	 */
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if(result.getFieldErrors() != null) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			Map<String, Object> errors = new HashMap<String, Object>();
			for (FieldError fieldError : fieldErrors) {
					System.out.println("错误的字段名 :" + fieldError.getField());
					System.out.println("错误的信息 :" + fieldError.getDefaultMessage());
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				return Msg.fail().add("errorFields", errors);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	
	/**
	 * 用  JSON处理PageInfo数据
	 * 导入 jackSON包
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn,
			Map<String,Object> map) {
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的查询,这个查询就是分页查询
		List<Employee> emp = employeeService.getAll();
		//使用 PageInfo包装查询后的结果,只需要将pageInfo交给页面就行了
		//封装了页面的详细信息,还包括有我们查询出来的数据,传入连续显示的页面
		PageInfo pageInfo = new PageInfo(emp,5);
		
		return Msg.success().add("pageInfo", pageInfo);
	}
			
	
//	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn,
			Map<String,Object> map) {
		//引入PageHelper 分页插件
		//在查询之前只需调用,传入页码,以及分页（每页的大小）
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的查询,这个查询就是分页查询
		List<Employee> emp = employeeService.getAll();
		//使用 PageInfo包装查询后的结果,只需要将pageInfo交给页面就行了
		//封装了页面的详细信息,还包括有我们查询出来的数据,传入连续显示的页面
		PageInfo pageInfo = new PageInfo(emp,5);
		map.put("pageInfo", pageInfo);
		return "list";
	}
	
	/**
	 * 规定：
	 * 批量删除 ： 传递的id 格式为  1-2-3
	 * 单个删除 ：1 
	 */
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		//区分批量删除和单个删除
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<Integer>();
 			String[] str_ids = ids.split("-");
			//组装id 的集合
 			for (String id : str_ids) {
				del_ids.add(Integer.parseInt(id));
			}
			employeeService.deleteBatch(del_ids);
		}else {
			int i = Integer.parseInt(ids);
			employeeService.deleteEmp(i);
		}
		
		return Msg.success();
	}
	
}
