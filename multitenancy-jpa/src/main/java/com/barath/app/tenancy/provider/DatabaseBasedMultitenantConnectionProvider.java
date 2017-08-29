package com.barath.app.tenancy.provider;

import java.util.Map.Entry;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.barath.app.tenancy.core.DatasourceMap;

public class DatabaseBasedMultitenantConnectionProvider  extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{

	
	private static final long serialVersionUID = 8880381577299434712L;
	
	private Map<String,DataSource> dataSources;	
	
	@Autowired
	private DatasourceMap datasourceMap;
	
	public DatabaseBasedMultitenantConnectionProvider(){
		
		Assert.notNull(datasourceMap, "Atleast one datasource should be present");
		this.dataSources=datasourceMap.getDataSources();
		
	}
	

	@Override
	protected DataSource selectAnyDataSource() {		
		
		Optional<Entry<String, DataSource>>  entry= this.dataSources.entrySet().stream().findFirst();
		if(entry.isPresent()){
			return entry.get().getValue();
		}
		return null;
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {		
		return this.dataSources.get(tenantIdentifier);
	}

}
