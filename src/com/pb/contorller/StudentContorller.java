package com.pb.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pb.entity.Student;
import com.pb.service.StudentService;

@Controller
public class StudentContorller {
	
	@Autowired
	StudentService  studentservice; 
	
	@RequestMapping(value="login.action")
	public String  getFindAll(){
		
		List<Student> list = studentservice.list();
		
		for(Student stu : list){
			System.out.println("学生姓名："+stu.getName()+"邮箱："+stu.getEmail()+list.size());
		}
		
		return "display";
	}

}
