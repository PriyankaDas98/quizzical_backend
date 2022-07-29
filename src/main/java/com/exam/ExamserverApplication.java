package com.exam;

import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ExamserverApplication {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {

		SpringApplication.run(ExamserverApplication.class, args);
	}

}
