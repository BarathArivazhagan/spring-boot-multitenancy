package com.barath.app.tenancy.configuration;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.barath.app.tenancy.interceptor.TenancyContextIntegrationFilter;
import com.barath.app.tenancy.provider.CurrentTenantProvider;
import com.barath.app.tenancy.provider.MultitenantConnectionProvider;
import com.barath.app.tenancy.strategy.HeaderTenantIdentificationStrategy;
import com.barath.app.tenancy.strategy.TenantIdentificationStrategy;

@Configuration
@ConditionalOnProperty(value="spring.multitenancy.enabled",matchIfMissing=true,havingValue="true")
@ComponentScan("com.barath.app.tenancy")
@AutoConfigureAfter(value=HibernateJpaAutoConfiguration.class)
public class MultitenantConfiguration {
	
	
	public static final String DEFAULT_HEADER_NAME="tenant-id";	
	
	
	@Bean
	@ConditionalOnMissingBean(value=TenantIdentificationStrategy.class)
	public HeaderTenantIdentificationStrategy strategy(){
		HeaderTenantIdentificationStrategy strategy= new HeaderTenantIdentificationStrategy();
		strategy.setHeaderName(DEFAULT_HEADER_NAME);
		return strategy;
	}
	
	@Bean
	public TenancyContextIntegrationFilter tenantFilter(TenantIdentificationStrategy strategy){
		TenancyContextIntegrationFilter filter= new TenancyContextIntegrationFilter();
		filter.setTenantIdentificationStrategyChain(Arrays.asList(strategy) );
		return filter;
	}
	
	@Bean
	@ConditionalOnMissingBean(value=CurrentTenantIdentifierResolver.class)
	public CurrentTenantIdentifierResolver currentTenantProvider(){
		return new CurrentTenantProvider();
	}
	
	@Bean
	@ConditionalOnMissingBean(value=MultiTenantConnectionProvider.class)
	public MultiTenantConnectionProvider multitenantProvider(){
		return new MultitenantConnectionProvider();
	}
	
	
	
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaProperties jpaProperties,
    		DataSource dataSource,
    		MultiTenantConnectionProvider multiTenantProvider,
    		CurrentTenantIdentifierResolver  currentTenantResolver) {
        Map<String, Object> properties = new HashMap<>();
        
        properties.putAll(jpaProperties.getHibernateProperties(dataSource));
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantProvider);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantResolver);
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.barath.app");
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);
        return em;
    }
    

 	@Bean
    public JpaVendorAdapter jpaVendorAdapter() {
 		
 		HibernateJpaVendorAdapter jpaVendorAdapter= new HibernateJpaVendorAdapter();        
        jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
    }

}
