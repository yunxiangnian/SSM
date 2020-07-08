package com.cloud.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.crud.bean.Employee;
import com.cloud.crud.bean.EmployeeExample;
import com.cloud.crud.bean.EmployeeExample.Criteria;
import com.cloud.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
		检测用户名是否可用
		true ： 代表可用  false: 代表不可用
	 */
	public boolean checkUser(String empName) {
		//MBG 逆向工程之后,
		EmployeeExample employeeExample = new EmployeeExample();
		Criteria criteria = employeeExample.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(employeeExample);
		return count == 0;
	}

	public Employee getEmp(Integer id) {
		return employeeMapper.selectByPrimaryKey(id); 
	}

	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where id in (1,2,3)
		criteria.andEmpIdIn(ids);
		
		employeeMapper.deleteByExample(example);
	}

	 
		
}
