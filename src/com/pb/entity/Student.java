package com.pb.entity;

import java.util.Date;

public class Student {
	Integer id;
	String name;
	String email;
	Date birthday;
	
	
	public Student() {
		super();
	}
	public Student(Integer id, String name, String email, Date birthday) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
}
