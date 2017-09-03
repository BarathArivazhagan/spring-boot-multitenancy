package com.barath.app.test.service;


import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.barath.app.test.entity.Customer;
import com.barath.app.test.exception.CustomerAlreadyExistsException;
import com.barath.app.test.exception.CustomerNotFoundException;
import com.barath.app.test.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private CustomerRepository customerRepo;
	
	public CustomerService(CustomerRepository customerRepository){
		this.customerRepo=customerRepository;
	}
	
	
	public Customer saveCustomer(Customer customer){
		
		if(logger.isInfoEnabled()){
			logger.info("Saving the customer with customer details {}",customer.toString());
		}
		if(isCustomerExists(customer)){
			throw new CustomerAlreadyExistsException("Customer Already Exists with Name");
		 	
		}		
		return this.customerRepo.save(customer);
		
	}
	
	public Customer getCustomer(String customerName){
		if(logger.isInfoEnabled()){
			logger.info("Getting the customer with customer name {}",customerName);
		}
		Customer customer=this.customerRepo.findByCustomerName(customerName);
		
		if(customer ==null){
			throw new CustomerNotFoundException("Customer with customer name "+customerName+" not found");
		}
		
		return customer;
		
	}
	
	public List<Customer> getCustomers(){
		if(logger.isInfoEnabled()){
			logger.info("Getting all the customers ");
		}
		return 	this.customerRepo.findAll();
		
	}
	
	public boolean isCustomerExists(Customer customer){
		
		return this.customerRepo.findByCustomerName(customer.getCustomerName()) !=null ? true:  false;
	}

}
