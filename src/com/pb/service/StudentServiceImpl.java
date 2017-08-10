package com.pb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pb.dao.StudentDao;
import com.pb.entity.Student;

@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	StudentDao studentdao;
	
	@Override
	public void save(Student stu) {
		
		
	}

	@Override
	public void delete(Integer id) {
		
		
	}

	@Override
	public void update(Student stu) {
		
		
	}

	@Override
	public Student findbyid(Integer id) {
		
		return null;
	}

	

	@Override
	public List<Student> list() {
		
		return studentdao.list();
	}

}
