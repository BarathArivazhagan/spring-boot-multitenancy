package com.barath.app.tenancy.provider;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barath.app.tenancy.context.TenancyContextHolder;

/**
 * 
 * An implementation of {@link CurrentTenantIdentifierResolver} to resolve the current tenant identifier.
 * 
 * @author Clint Morgan
 * @author barath.arivazhagan
 *
 */
public class CurrentTenantProvider implements CurrentTenantIdentifierResolver {
	
	
	private static final Logger logger=LoggerFactory.getLogger(CurrentTenantProvider.class);
	
	
	

	@Override
	public String resolveCurrentTenantIdentifier() {
		
		String tenantId = TenancyContextHolder.getContext().getTenant().getIdentity();
        if (tenantId != null) {
            return tenantId;
        }else{
        	tenantId=TenancyContextHolder.getStrategy().createEmptyContext().getTenant().getIdentity();
        }       
        if(logger.isInfoEnabled()){
        	logger.info("Current Tenant ID {}",tenantId);
        }
        return tenantId;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		
		return true;
	}

}
