package com.barath.app.test;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.barath.app.tenancy.annotation.EnableMultiTenancy;

@SpringBootApplication
@EnableMultiTenancy
public class TestApplication {
	
	public static void main(String[] args){
		SpringApplication.run(TestApplication.class, args);
	}
	
	@Bean
	public DataSource dataSource(){
		
		EmbeddedDatabase dataSource=new EmbeddedDatabaseBuilder().addScript("customerDDL.sql")
					.setType(EmbeddedDatabaseType.HSQL).build();
		
		 return dataSource;
	}

}
