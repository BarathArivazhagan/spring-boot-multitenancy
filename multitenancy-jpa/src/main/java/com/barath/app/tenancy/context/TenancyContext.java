

package com.barath.app.tenancy.context;

import java.io.Serializable;

import com.barath.app.tenancy.core.Tenant;


/**
 * a context in which tenancy can be defined
 * 
 * @author Clint Morgan 
 * @author barath.arivazhagan
 */
public interface TenancyContext extends Serializable {
	/**
	 * Obtains the current tenant.
	 * 
	 * @return the <code>Tenant</code> or <code>null</code> if no tenancy information is available.
	 */
	Tenant getTenant();

	/**
	 * Changes the current tenant, or removes the tenancy information.
	 * 
	 * @param tenant
	 *            the new <code>Tenant</code>, or <code>null</code> if no further tenancy information should be stored
	 */
	void setTenant(Tenant tenant);
}
