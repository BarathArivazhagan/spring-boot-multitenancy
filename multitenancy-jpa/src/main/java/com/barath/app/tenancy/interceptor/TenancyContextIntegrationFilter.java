
package com.barath.app.tenancy.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.barath.app.tenancy.context.TenancyContext;
import com.barath.app.tenancy.context.TenancyContextHolder;
import com.barath.app.tenancy.core.Tenant;
import com.barath.app.tenancy.provider.DefaultTenant;
import com.barath.app.tenancy.provider.DefaultTenantProvider;
import com.barath.app.tenancy.provider.TenantProvider;
import com.barath.app.tenancy.strategy.TenantIdentificationStrategy;

import org.springframework.web.filter.GenericFilterBean;

/**
 * Responsible for setting and removing the {@link TenancyContext tenancy context} for the scope of every request. This
 * filter should be installed before any components that need access to the {@link TenancyContext tenancy context}.
 * 
 * @author Clint Morgan
 * @author barath.arivazhagan
 * 
 * @see TenancyContext
 * @see TenancyContextHolder
 */
public class TenancyContextIntegrationFilter extends GenericFilterBean {

	private List<TenantIdentificationStrategy> tenantIdentificationStrategyChain;

	private TenantProvider tenantProvider = new DefaultTenantProvider();
	
	private String defaultTenantIdentifier=null;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		TenancyContext contextBeforeChainExecution = determineTenancyContext(request);

		try {
			TenancyContextHolder.setContext(contextBeforeChainExecution);

			chain.doFilter(req, res);

		} finally {
			// Crucial removal of ContextHolder contents - do this
			// before anything else.
			TenancyContextHolder.clearContext();
		}

	}

	private TenancyContext determineTenancyContext(HttpServletRequest request) {
		TenancyContext tenancyContext = TenancyContextHolder.createEmptyContext();
		Tenant tenant=determineTenant(request);
		tenancyContext.setTenant(tenant);
		return tenancyContext;
	}

	/**
	 * Determine the tenant based on a given request. Default implementation goes through the identification strategies
	 * in order. The first identification strategy to find a tenant identification in the request will be used, via the
	 * tenantProvider, to locate the tenant.
	 * 
	 * @param request
	 * @return the tenant context for the given httpRequest. <code>null</code> is a valid return value.
	 */
	protected Tenant determineTenant(HttpServletRequest request) {
		
		Tenant tenant=null;
		for (TenantIdentificationStrategy tenantIdentificationStrategy : tenantIdentificationStrategyChain) {
			String tenantId = tenantIdentificationStrategy.identifyTenant(request);
			if (tenantId != null) {
				tenant=tenantProvider.findTenant(tenantId);
			}
		}
		
		if(tenant == null){
			DefaultTenant defaultTenant =new DefaultTenant();
			defaultTenant.setTenantIdentifier(getDefaultTenantIdentifier());
			tenant=defaultTenant;
		}
		return tenant;
	}

	/**
	 * Set the tenant identification strategy chain. The first member of the chain to identify a tenant will be used.
	 * 
	 * @param tenantIdentificationStrategyChain
	 */
	public void setTenantIdentificationStrategyChain(
			List<TenantIdentificationStrategy> tenantIdentificationStrategyChain) {
		this.tenantIdentificationStrategyChain = tenantIdentificationStrategyChain;
	}

	public void setTenantProvider(TenantProvider tenantProvider) {
		this.tenantProvider = tenantProvider;
	}

	public String getDefaultTenantIdentifier() {
		return defaultTenantIdentifier;
	}

	public void setDefaultTenantIdentifier(String defaultTenantIdentifier) {
		this.defaultTenantIdentifier = defaultTenantIdentifier;
	}
	
	
	
}