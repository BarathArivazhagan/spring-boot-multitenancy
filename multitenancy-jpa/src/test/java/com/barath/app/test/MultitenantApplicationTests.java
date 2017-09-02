package com.barath.app.test;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.barath.app.tenancy.context.TenancyContextHolder;
import com.barath.app.tenancy.provider.DefaultTenant;
import com.barath.app.test.entity.Customer;
import com.barath.app.test.repository.CustomerRepository;
import com.barath.app.test.service.CustomerService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes=TestApplication.class,webEnvironment=WebEnvironment.RANDOM_PORT)
public class MultitenantApplicationTests {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String TENANT_A="tenant_a";
	private static final String TENANT_B="tenant_b";
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void saveCustomerTestWithTenantA() {
		
		setTenantContext(TENANT_A);
		Customer customer=customerService.saveCustomer(new Customer(4L,"DHONI"));
		assertEquals("DHONI", customer.getCustomerName());
		
		
	}
	
	
	@Test
	public void saveCustomerTestWithTenantB() {
		
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
	
	
	  protected void setTenantContext(String tenantIdentifier){
		  
		  if(logger.isInfoEnabled()){
			  logger.info("setting the tenant to {} ", tenantIdentifier);
		  }
		  DefaultTenant tenant=new DefaultTenant();
		  tenant.setTenantIdentifier(tenantIdentifier);
		  TenancyContextHolder.getContext().setTenant(tenant);
		  
	  }
	
	
	
	
	
	
	
	
	
	
	
}
