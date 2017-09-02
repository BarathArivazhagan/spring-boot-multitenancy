

package com.barath.app.tenancy.provider;

import com.barath.app.tenancy.core.Tenant;

/**
 * A means of providing a tenant for any given tenant identity
 * 
 * @author Clint Morgan
 * @author barath.arivazhagan
 */
public interface TenantProvider {

	/**
	 * Attempt to find a tenant based on a given tenant identity identity.
	 * 
	 * @param identity
	 *            the identify of a tenant, or null if there is none
	 * @return a tenant for the corresponding identity, or <code>null</code> if the given identity has no corresponding
	 *         tenant.
	 */
	Tenant findTenant(String identity);
}
