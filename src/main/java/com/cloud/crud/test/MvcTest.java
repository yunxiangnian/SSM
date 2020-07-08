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
 * ʹ�� Spring ����ģ�����������,���� CRUD����ȷ��
 * Spring4���Ե�ʱ��,��Ҫservlet 3.0 ֧��, ��Ȼ�ᱨ NoClassDefFoundError
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:spring.xml","file:src/main/webapp/WEB-INF/springMVC-servlet.xml"})
public class MvcTest {
	@Autowired
	WebApplicationContext context;
	//�����mvc����,��ȡ������
	MockMvc mockMvc;
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		//ģ�ⷢ������
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
				.andReturn();
		
		//���Է���ֵ,����ɹ���,�������л��� pageInfo
		MockHttpServletRequest request = result.getRequest();
		
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		
		System.out.println("��ǰҳ�� :" + pageInfo.getPageNum());
		System.out.println("��ҳ�� :" + pageInfo.getPages());
		System.out.println("�ܼ�¼�� :" + pageInfo.getTotal());
		int[] nums = pageInfo.getNavigatepageNums();
		for (int i = 0; i < nums.length; i++) {
			System.out.print( "" + nums[i]);
		}
		
		//��ȡԱ������
		List<Employee> list = pageInfo.getList();
		for (Employee employee : list) {
			System.out.println("ID :" + employee.getEmpId() + "==>name" + employee.getEmpName());;
		}
	}
	
}
