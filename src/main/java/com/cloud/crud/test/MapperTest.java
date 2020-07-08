package com.cloud.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
/**
 * ���� dao��Ĺ���
 * 
 * �Ƽ� spring����Ŀʹ��spring��Ԫ����
 * 1������ Spring-test ģ��
 * 2��ʹ�� @ContextConfiguration ָ��Spring�����ļ���λ��
 * 3��ֱ�� AutoWired Ҫʹ�õ��������
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
	 * ����DepartmentMapper
	 */
	@Test
	public void test() {
//		System.out.println(departmentMapper);
		//1�����뼸������
//		Department department = new Department(null,"������");
//		Department department2 = new Department(null,"���Բ�");
//		departmentMapper.insertSelective(department);
//		departmentMapper.insertSelective(department2);
		
		//2������Ա��
//		employeeMapper.insertSelective(new Employee(null, "Jack", "1", "jack@cloud.com", 2));
		//3������������Ա��,ʹ�ÿ������������SqlSession
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String substring = UUID.randomUUID().toString().substring(0, 5);
			mapper.insertSelective(new Employee(null, substring, i%2==0 ? "1":"0", substring+"@cloud.com", i%2==0 ? 1:2));
		}
	}

}
