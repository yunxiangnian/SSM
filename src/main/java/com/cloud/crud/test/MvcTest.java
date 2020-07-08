package com.cloud.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cloud.crud.bean.Employee;
import com.github.pagehelper.PageInfo;

/*
 * 使用 Spring 测试模块测试请求功能,测试 CRUD的正确性
 * Spring4测试的时候,需要servlet 3.0 支持, 不然会报 NoClassDefFoundError
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:spring.xml","file:src/main/webapp/WEB-INF/springMVC-servlet.xml"})
public class MvcTest {
	@Autowired
	WebApplicationContext context;
	//虚拟的mvc请求,获取处理结果
	MockMvc mockMvc;
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		//模拟发送请求
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
				.andReturn();
		
		//测试返回值,请求成功后,请求域中会有 pageInfo
		MockHttpServletRequest request = result.getRequest();
		
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		
		System.out.println("当前页码 :" + pageInfo.getPageNum());
		System.out.println("总页码 :" + pageInfo.getPages());
		System.out.println("总记录数 :" + pageInfo.getTotal());
		int[] nums = pageInfo.getNavigatepageNums();
		for (int i = 0; i < nums.length; i++) {
			System.out.print( "" + nums[i]);
		}
		
		//获取员工数据
		List<Employee> list = pageInfo.getList();
		for (Employee employee : list) {
			System.out.println("ID :" + employee.getEmpId() + "==>name" + employee.getEmpName());;
		}
	}
	
}
