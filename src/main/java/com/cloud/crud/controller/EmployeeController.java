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
 * ����Ա���� CRUD ����
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * �������:
	 * ����Ҫ����֧��ֱ�ӷ���PUT֮�������Ҫ��װ�������е�����
	 * ������HttpPutFormContentFilter
	 * �������ã����������е����ݰ�װ��һ��Map
	 * 		request�����°�װ request.getParameter()����д,�ͻ���Լ���װ��map��ȡֵ
	 * Ա�����·��� 
	 * ���ֱ�ӷ��� PUT ����
	 * ��װ������
	 * ��Ҫ���µ�Ա������Employee [empId=1025, empName=null, gender=null, email=null, dId=null, department=null]
	 * 
	 * ����: ��������������,���Ǹ��²���,�޷���װEmployee
	 * ԭ��: 
	 * 		Tomcat:
	 * 			1�����������е����ݷ�װ��Map,��װ��Map
	 * 			2��request.getParameter("empName")�ͻ��map��ȡֵ
	 * 			3��SpringMVC��װPOJO�����ʱ��,
	 * 					���POJO�е�ÿ������ֵ,�����Map����request.getParameter("empName")��ȡ
	 * 	AJAX ����put����������Ѫ��
	 * 		PUT�����������е�����,request.getParameter("empName")�ò�������
	 * 		Tomcatһ����PUT���󣬾Ͳ����װMap��ֻ����POST�����ʱ��Ż��װ������ΪMap
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
		System.out.println("��Ҫ���µ�Ա������" + employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	//��ѯ��Ӧ��id Ա��
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		
		Employee employee = employeeService.getEmp(id);
		
		return Msg.success().add("emp", employee);
	}
	
	
	/**
	 * ����û����Ƿ����
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public Msg checkUser(@RequestParam("empName")String empName) {
		//���ж��û����Ƿ�Ϸ�
		String regex = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regex)) {
			return Msg.fail().add("va_msg", "�û���������4-16λ���ֺ�2-5λ�����ַ�");
		}
		
		boolean b = employeeService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}
	
	/**
	 * 1��Ա��У�� 
	 *  1��֧��JSR303
	 *  2������ Hibernate-Validator
	 */
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if(result.getFieldErrors() != null) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			Map<String, Object> errors = new HashMap<String, Object>();
			for (FieldError fieldError : fieldErrors) {
					System.out.println("������ֶ��� :" + fieldError.getField());
					System.out.println("�������Ϣ :" + fieldError.getDefaultMessage());
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				return Msg.fail().add("errorFields", errors);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	
	/**
	 * ��  JSON����PageInfo����
	 * ���� jackSON��
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn,
			Map<String,Object> map) {
		PageHelper.startPage(pn, 5);
		//startPage��������Ĳ�ѯ,�����ѯ���Ƿ�ҳ��ѯ
		List<Employee> emp = employeeService.getAll();
		//ʹ�� PageInfo��װ��ѯ��Ľ��,ֻ��Ҫ��pageInfo����ҳ�������
		//��װ��ҳ�����ϸ��Ϣ,�����������ǲ�ѯ����������,����������ʾ��ҳ��
		PageInfo pageInfo = new PageInfo(emp,5);
		
		return Msg.success().add("pageInfo", pageInfo);
	}
			
	
//	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn,
			Map<String,Object> map) {
		//����PageHelper ��ҳ���
		//�ڲ�ѯ֮ǰֻ�����,����ҳ��,�Լ���ҳ��ÿҳ�Ĵ�С��
		PageHelper.startPage(pn, 5);
		//startPage��������Ĳ�ѯ,�����ѯ���Ƿ�ҳ��ѯ
		List<Employee> emp = employeeService.getAll();
		//ʹ�� PageInfo��װ��ѯ��Ľ��,ֻ��Ҫ��pageInfo����ҳ�������
		//��װ��ҳ�����ϸ��Ϣ,�����������ǲ�ѯ����������,����������ʾ��ҳ��
		PageInfo pageInfo = new PageInfo(emp,5);
		map.put("pageInfo", pageInfo);
		return "list";
	}
	
	/**
	 * �涨��
	 * ����ɾ�� �� ���ݵ�id ��ʽΪ  1-2-3
	 * ����ɾ�� ��1 
	 */
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		//��������ɾ���͵���ɾ��
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<Integer>();
 			String[] str_ids = ids.split("-");
			//��װid �ļ���
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
