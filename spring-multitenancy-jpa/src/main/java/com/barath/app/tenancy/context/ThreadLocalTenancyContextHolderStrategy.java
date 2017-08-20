

package com.barath.app.tenancy.context;

import org.springframework.util.Assert;

import com.barath.app.tenancy.core.Tenant;

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
	private static final String DEFAULT_TENANT_ID="testdb";

	public void clearContext() {
		contextHolder.set(null);
	}

	public TenancyContext getContext() {
		TenancyContext ctx = contextHolder.get();

		if (ctx == null) {
			ctx = createEmptyContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}

	public void setContext(TenancyContext context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}

	public TenancyContext createEmptyContext() {
		DefaultTenancyContext context= new DefaultTenancyContext();
		context.setTenant(new Tenant() {
			
			@Override
			public String getIdentity() {
				return DEFAULT_TENANT_ID;
			}
		});
		return context;
	}
}
