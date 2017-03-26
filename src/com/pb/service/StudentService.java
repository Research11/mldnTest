package com.pb.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pb.entity.Student;

public interface StudentService {
	void save(Student stu);
	void delete(Integer id);
	void update(Student stu);
	Student findbyid(Integer id);
	List<Student> list();
}
