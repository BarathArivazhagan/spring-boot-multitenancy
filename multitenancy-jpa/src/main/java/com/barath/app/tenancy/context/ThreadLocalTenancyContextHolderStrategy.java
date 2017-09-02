

package com.barath.app.tenancy.context;

import org.springframework.util.Assert;

import com.barath.app.tenancy.core.Tenant;
import com.barath.app.tenancy.provider.DefaultTenant;

/**
 * A <code>ThreadLocal</code>-based implementation of {@link TenancyContextHolderStrategy}.
 * 
 * @author Clint Morgan 
 * @author barath.arivazhagan
 * 
 * @see java.lang.ThreadLocal
 */
public class ThreadLocalTenancyContextHolderStrategy implements TenancyContextHolderStrategy {
	private static final ThreadLocal<TenancyContext> contextHolder = new ThreadLocal<TenancyContext>();
	
	
	private String tenantIdentifier;

	public void clearContext() {
		contextHolder.set(null);
	}

	public TenancyContext getContext() {
		TenancyContext ctx = contextHolder.get();

		if (ctx == null) {
			ctx = createEmptyContext();			
		}
		if(ctx.getTenant() == null){
			DefaultTenant tenant=new DefaultTenant();
			tenant.setTenantIdentifier(getTenantIdentifier());
			ctx.setTenant(tenant); 
		}
		contextHolder.set(ctx);
		return ctx;
	}

	public void setContext(TenancyContext context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}

	public TenancyContext createEmptyContext() {
		 return new DefaultTenancyContext();
		
	}
	
	public void setDefaultIdentifier(String tenantIdentifier){
		setTenantIdentifier(tenantIdentifier);
		
	}

	public String getTenantIdentifier() {
		return tenantIdentifier;
	}

	public void setTenantIdentifier(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}
	
	
}
