package com.barath.app.test;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barath.app.tenancy.context.TenancyContextHolder;
import com.barath.app.tenancy.provider.DefaultTenant;
import com.barath.app.test.entity.Customer;
import com.barath.app.test.exception.CustomerAlreadyExistsException;
import com.barath.app.test.exception.CustomerNotFoundException;
import com.barath.app.test.repository.CustomerRepository;
import com.barath.app.test.service.CustomerService;


public class MultitenantApplicationTests extends AbstractSpringRunner{	
	

	private static final String TENANT_A="tenant_a";
	private static final String TENANT_B="tenant_b";
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void saveCustomerWithTenantA() {
		
		setTenantContext(TENANT_A);
		Customer customer=customerService.saveCustomer(new Customer(4L,"DHONI"));
		assertEquals("DHONI", customer.getCustomerName());
		
		
	}
	
	
	@Test
	public void saveCustomerWithTenantB() {
		
		setTenantContext(TENANT_B);
		Customer customer=customerService.saveCustomer(new Customer(4L,"SMITH"));
		assertEquals("SMITH", customer.getCustomerName());		
		assertEquals(Long.valueOf(4), customer.getCustomerId());
		
	}
	
	@Test
	public void getExistingCustomerFromTenantA(){
		
		setTenantContext(TENANT_A);
		Customer customer=customerService.getCustomer("RAMESH");
		assertEquals("RAMESH", customer.getCustomerName());	
	}
	
	
	@Test
	public void getExistingCustomerFromTenantB(){
		
		setTenantContext(TENANT_B);
		Customer customer=customerService.getCustomer("BILL");
		assertEquals("BILL", customer.getCustomerName());	
	}
	
	

	@Test
	public void getAllCustomersFromTenantA(){
		
		setTenantContext(TENANT_A);
		List<Customer> customers=customerService.getCustomers();
		List<String> expectedCustomers=Arrays.asList("RAMESH","SURESH","MAHESH");
		assertEquals(expectedCustomers, customers.stream().map(Customer::getCustomerName).limit(3).collect(Collectors.toList()));
	}
	
	
	@Test
	public void getAllCustomersFromTenantB(){
		
		setTenantContext(TENANT_B);
		List<Customer> customers=customerService.getCustomers();
		List<String> expectedCustomers=Arrays.asList("BILL","HAYDEN","STEVE");
		assertEquals(expectedCustomers, customers.stream().map(Customer::getCustomerName).limit(3).collect(Collectors.toList()));
	}
	
	
	@Test(expected=CustomerAlreadyExistsException.class)
	public void saveExistingCustomerWithTenantA(){
		
		setTenantContext(TENANT_A);
		Customer customer=new Customer(5L, "KOHLI");
		customer=customerService.saveCustomer(customer);
		customerService.saveCustomer(customer);
	}
	

	@Test(expected=CustomerNotFoundException.class)
	public void expectCustomerNotFoundException(){
		
		setTenantContext(TENANT_A);
		customerService.getCustomer("INDIA");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
