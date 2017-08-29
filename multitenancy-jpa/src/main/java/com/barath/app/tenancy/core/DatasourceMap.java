package com.barath.app.tenancy.core;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;


/**
 * 
 * @author barath.arivazhagan
 *
 */

@ConditionalOnProperty(value="multitenancy.type",havingValue="database")
@Component
public class DatasourceMap {
	
	private DatasourcePropertiesMap props;
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public DatasourceMap(DatasourcePropertiesMap props){
		
	}
	
	private Map<String,DataSource> dataSources;	
	
	public Map<String, DataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(Map<String, DataSource> dataSources) {
		this.dataSources = dataSources;
	}


	@PostConstruct
	public void initializeDataSources(){
		
		if(props !=null && props.getDataSourceProperties() !=null ){
			
			 Map<String,DataSource> sources=new HashMap<>(props.getDataSourceProperties().size());
			props.getDataSourceProperties().entrySet().forEach( (entry)-> {
				
				DataSourceProperties sourceProps=entry.getValue();
				if(logger.isInfoEnabled()){
					logger.info("Configuring datasource fwith tenant id {} and properties {}",entry.getKey(),entry.getValue());
				}
				DataSource dataSource=DataSourceBuilder.create()
									  .url(sourceProps.getUrl()).username(sourceProps.getUsername())
									  .password(sourceProps.getPassword())
									  .driverClassName(sourceProps.getDriverClassName()).build();
				sources.put(entry.getKey(),dataSource);
				
			});
			
			
		}
		
	}

}
