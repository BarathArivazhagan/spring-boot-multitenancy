package com.barath.app.tenancy.configuration;


import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.barath.app.tenancy.constant.TenantConstant;
import com.barath.app.tenancy.context.TenancyContextHolder;
import com.barath.app.tenancy.interceptor.TenancyContextIntegrationFilter;
import com.barath.app.tenancy.provider.CurrentTenantProvider;
import com.barath.app.tenancy.provider.DatabaseBasedMultitenantConnectionProvider;
import com.barath.app.tenancy.provider.SchemaBasedMultitenantConnectionProvider;
import com.barath.app.tenancy.strategy.HeaderTenantIdentificationStrategy;
import com.barath.app.tenancy.strategy.TenantIdentificationStrategy;

@Configuration
@ConditionalOnProperty(value="multitenancy.enabled",matchIfMissing=true,havingValue="true")
@ComponentScan("com.barath.app.tenancy")
@AutoConfigureAfter(value=HibernateJpaAutoConfiguration.class)
public class MultitenantConfiguration {
	
	
	public static final String DEFAULT_HEADER_NAME="tenant-id";	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Value("${spring.jpa.database:mysql}")
	private String databaseType;
	
	@Value("${entity.packages:com.accn.ppes.magellan.entity}")
	private String packagesToScan;
	
	@Value("${multitenancy.type:schema}")
	private String tenantType;
	
	@Value("${multitenancy.default-tenant:microservices}")
	private String defaultTenant;
	
	
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
		filter.setDefaultTenantIdentifier(defaultTenant);
		TenancyContextHolder.setDefaultIdentifier(defaultTenant);
		return filter;
	}
	
	@Bean
	@ConditionalOnMissingBean(value=CurrentTenantIdentifierResolver.class)
	public CurrentTenantIdentifierResolver currentTenantProvider(){
		return new CurrentTenantProvider();
	}
	
	@Bean
	@ConditionalOnProperty(value="multitenancy.type",matchIfMissing=true,havingValue="schema")
	@ConditionalOnMissingBean(value=MultiTenantConnectionProvider.class)
	public MultiTenantConnectionProvider schemaMultitenantProvider(){
		
		if(logger.isInfoEnabled()){
			logger.info(TenantConstant.LOG_HEADER+"{} configured multi tenant provider",tenantType);
		}
		SchemaBasedMultitenantConnectionProvider schemaMultitenantProvider= new SchemaBasedMultitenantConnectionProvider();
		schemaMultitenantProvider.setDatabaseName(databaseType);
		return schemaMultitenantProvider;
	}
	
	@Bean
	@ConditionalOnProperty(value="multitenancy.type",havingValue="database")
	@ConditionalOnMissingBean(value=MultiTenantConnectionProvider.class)
	public MultiTenantConnectionProvider databaseMultitenantProvider(){		

		if(logger.isInfoEnabled()){
			logger.info(TenantConstant.LOG_HEADER+"{} configured multi tenant provider",tenantType);
		}
		return new DatabaseBasedMultitenantConnectionProvider();
	}
	
	
	
	
    @Bean    
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaProperties jpaProperties,
    		DataSource dataSource,
    		MultiTenantConnectionProvider multiTenantProvider,
    		CurrentTenantIdentifierResolver  currentTenantResolver) {
        Map<String, Object> properties = new HashMap<>();       
        properties.putAll(jpaProperties.getHibernateProperties(dataSource));
        properties.put(Environment.MULTI_TENANT,getMultiTenancyStrategy(tenantType));
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantProvider);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantResolver);
        properties.putAll(getConnectionPoolProps());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(packagesToScan);
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);
        return em;
    }
    
    
   

 	@Bean
    public JpaVendorAdapter jpaVendorAdapter() {
 		
 		HibernateJpaVendorAdapter jpaVendorAdapter= new HibernateJpaVendorAdapter();        
		jpaVendorAdapter.setShowSql(true);
		
		jpaVendorAdapter.setDatabase(getDatabaseType(databaseType));
		return jpaVendorAdapter;
    }
 	
 	private Map<String,String> getConnectionPoolProps(){
 	
 		Map<String,String> poolProperties=new HashMap<>();
 		poolProperties.put("hibernate.c3p0.min_size", "5");
 		poolProperties.put("hibernate.c3p0.max_size", "10");
 		poolProperties.put("hibernate.c3p0.max_statements", "50");
 		poolProperties.put("hibernate.c3p0.timeout", "0"); 		
 		return poolProperties;
 	}
 	
 	private Database getDatabaseType(String databaseType){
 		
 		Database dbType=Database.MYSQL; 
 		
 		switch(databaseType){
 		
	 		case "mysql" : dbType= Database.MYSQL;break;
	 		case "hsql" : dbType=Database.HSQL;break;
	 		case "h2" : dbType=Database.H2;break;
	 		case "oracle" : dbType=Database.ORACLE;break;
	 		case "postgresql" : dbType=Database.POSTGRESQL;break;
 		
 		}
 		
 		return dbType;
 	}
 	
 	private MultiTenancyStrategy getMultiTenancyStrategy(String type){
 		
 		MultiTenancyStrategy strategyType=MultiTenancyStrategy.SCHEMA; 
 		
 		switch(type){
 		
	 		case "database" : strategyType= MultiTenancyStrategy.DATABASE;break;
	 		case "schema" : strategyType=MultiTenancyStrategy.SCHEMA;break;
	 		
 		
 		}
 		
 		return strategyType;
 		
 	}

}
