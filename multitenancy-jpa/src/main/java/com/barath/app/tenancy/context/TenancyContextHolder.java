

package com.barath.app.tenancy.context;


/**
 * Associates a given {@link TenancyContext} with the current execution.
 * <p>
 * This class provides a series of static methods that delegate to an instance of {@link TenancyContextHolderStrategy}.
 * The purpose of the class is to provide a convenient way to specify the strategy that should be used for a given JVM.
 * This is a JVM-wide setting, since everything in this class is <code>static</code> to facilitate ease of use in
 * calling code.
 * 
 * @author Clint Morgan 
 * @author barath.arivazhagan
 * 
 */
public class TenancyContextHolder {

	private static TenancyContextHolderStrategy strategy = new ThreadLocalTenancyContextHolderStrategy();

	
	/**
	 * Explicitly clear the tenacy context.
	 * 
	 */
	public static void clearContext() {
		strategy.clearContext();
	}

	/**
	 * Obtain the current <code>TenancyContext</code>.
	 * 
	 * @return the tenancy context (never <code>null</code>)
	 */
	public static TenancyContext getContext() {
		return strategy.getContext();
	}

	/**
	 * Associates a new <code>TenancyContext</code> with the current context of execution.
	 * 
	 * @param context
	 *            the new <code>TenancyContext</code> (may not be <code>null</code>)
	 */

	public static void setContext(TenancyContext context) {
		strategy.setContext(context);
	}

	/**
	 * Delegates the creation of a new, empty context to the configured strategy.
	 */
	public static TenancyContext createEmptyContext() {
		return strategy.createEmptyContext();
	}

	/**
	 * Allows retrieval of the context strategy.
	 * 
	 * @return the configured strategy for storing the tenancy context.
	 */
	public static TenancyContextHolderStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Set the context strategy.
	 * 
	 * @param strategy
	 *            the configured strategy for storing the tenancy context.
	 */
	public static void setStrategy(TenancyContextHolderStrategy strategy) {
		if (strategy == null) {
			throw new IllegalArgumentException();
		}
		TenancyContextHolder.strategy = strategy;
	}
	
	/**
	 * Set the default identifier
	 * 
	 * @param tenantIdentifier
	 * 
	 */
	public static void setDefaultIdentifier(String tenantIdentifier){
		strategy.setDefaultIdentifier(tenantIdentifier);
	}

}
