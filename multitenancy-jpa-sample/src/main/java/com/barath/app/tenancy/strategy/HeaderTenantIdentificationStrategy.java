package com.barath.app.tenancy.strategy;

import javax.servlet.http.HttpServletRequest;



/**
 * A {@link TenantIdentificationStrategy strategy} which looks for tenant identity from a given request header.
 * 
 * @author Clint Morgan 
 * 
 */
public class HeaderTenantIdentificationStrategy implements TenantIdentificationStrategy {

	private String headerName;

	@Override
	public String identifyTenant(HttpServletRequest request) {
		return request.getHeader(headerName);
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

}
