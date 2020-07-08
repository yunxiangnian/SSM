package com.cloud.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.crud.bean.Department;
import com.cloud.crud.dao.DepartmentMapper;

@Service
public class DepartmentService {

	@Autowired
	DepartmentMapper departmentMapper;
	
	public List<Department> getDepts() {
		return departmentMapper.selectByExample(null);
	}

	
}
