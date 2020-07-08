package com.cloud.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
/**
 * 测试 dao层的工作
 * 
 * 推荐 spring的项目使用spring单元测试
 * 1、导入 Spring-test 模块
 * 2、使用 @ContextConfiguration 指定Spring配置文件的位置
 * 3、直接 AutoWired 要使用的组件即可
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloud.crud.bean.Employee;
import com.cloud.crud.dao.DepartmentMapper;
import com.cloud.crud.dao.EmployeeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring.xml"})
public class MapperTest {

	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	/**
	 * 测试DepartmentMapper
	 */
	@Test
	public void test() {
//		System.out.println(departmentMapper);
		//1、插入几个部门
//		Department department = new Department(null,"开发部");
//		Department department2 = new Department(null,"测试部");
//		departmentMapper.insertSelective(department);
//		departmentMapper.insertSelective(department2);
		
		//2、插入员工
//		employeeMapper.insertSelective(new Employee(null, "Jack", "1", "jack@cloud.com", 2));
		//3、批量插入多个员工,使用可以批量插入的SqlSession
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String substring = UUID.randomUUID().toString().substring(0, 5);
			mapper.insertSelective(new Employee(null, substring, i%2==0 ? "1":"0", substring+"@cloud.com", i%2==0 ? 1:2));
		}
	}

}
