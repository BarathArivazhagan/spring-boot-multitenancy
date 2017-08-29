package com.barath.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.barath.app.tenancy.annotation.EnableMultiTenancy;

@SpringBootApplication
@EnableMultiTenancy
public class MultitenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultitenantApplication.class, args);
	}
}
