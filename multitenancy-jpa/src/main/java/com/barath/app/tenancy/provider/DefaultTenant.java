

package com.barath.app.tenancy.provider;

import com.barath.app.tenancy.core.Tenant;

/**
 * A default implementation of a {@link Tenant}. In addition to the tenant identity, it provides a link to arbitrary
 * data for the tenant.
 * 
 * @author Clint Morgan
 * @author barath.arivazhagan
 */
public class DefaultTenant implements Tenant {

	private static final long serialVersionUID = 1L;
	
	private String tenantIdentifier;
	
	
	public DefaultTenant() {
		
	}

	@Override
	public String getIdentity() {
		
		return getTenantIdentifier();
	}

	public String getTenantIdentifier() {
		return tenantIdentifier;
	}

	public void setTenantIdentifier(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}
	
	
	
	

}
