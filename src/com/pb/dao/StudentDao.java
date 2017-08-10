package com.pb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pb.entity.Student;

public interface StudentDao {
	void save(Student stu);
	void delete(Integer id);
	void update(Student stu);
	Student findbyid(Integer id);
	List<Student> list();
}
