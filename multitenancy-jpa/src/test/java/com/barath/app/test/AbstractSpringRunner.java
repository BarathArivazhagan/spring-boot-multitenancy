package com.barath.app.test;

import java.lang.invoke.MethodHandles;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.barath.app.tenancy.context.TenancyContextHolder;
import com.barath.app.tenancy.provider.DefaultTenant;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes=TestApplication.class,webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class AbstractSpringRunner {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	 protected void setTenantContext(String tenantIdentifier){
		  
		  if(logger.isInfoEnabled()){
			  logger.info("setting the tenant to {} ", tenantIdentifier);
		  }
		  DefaultTenant tenant=new DefaultTenant();
		  tenant.setTenantIdentifier(tenantIdentifier);
		  TenancyContextHolder.getContext().setTenant(tenant);
		  
	  }
	

}
