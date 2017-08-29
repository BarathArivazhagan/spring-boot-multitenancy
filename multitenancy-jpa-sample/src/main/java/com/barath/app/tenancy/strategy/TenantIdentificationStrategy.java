
package com.barath.app.tenancy.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * A strategy for identifying tenants from a {@link HttpServletRequest}.
 * 
 * @author barath.arivazhagan
 */
public interface TenantIdentificationStrategy {

	String identifyTenant(HttpServletRequest request);
}
