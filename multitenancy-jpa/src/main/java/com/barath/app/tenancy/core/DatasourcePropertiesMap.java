package com.barath.app.tenancy.core;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value="multitenancy.type",havingValue="database")
@Configuration
@ConfigurationProperties(ignoreUnknownFields=true,prefix="multitenancy.datasources")
public class DatasourcePropertiesMap {
	
	private Map<String, DataSourceProperties> dataSourceProperties;

	public Map<String, DataSourceProperties> getDataSourceProperties() {
		return dataSourceProperties;
	}

	public void setDataSourceProperties(Map<String, DataSourceProperties> dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}
	
	

}
