package com.barath.app.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barath.app.test.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	
	Customer findByCustomerName(String customerName);

}
