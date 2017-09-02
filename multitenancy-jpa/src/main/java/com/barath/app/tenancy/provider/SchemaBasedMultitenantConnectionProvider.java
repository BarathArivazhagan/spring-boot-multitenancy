package com.barath.app.tenancy.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barath.app.tenancy.context.TenancyContextHolder;

/**
 * Default implementation of MultiTenantConnectionProvider see {@link MultiTenantConnectionProvider}.
 * 
 * 
 * @author barath.arivazhagan
 *
 */
public class SchemaBasedMultitenantConnectionProvider extends AbstractDatabaseSwitchingDataSource implements MultiTenantConnectionProvider {
	
	private static final long serialVersionUID = -2572262849403769063L;
	private static final Logger logger=LoggerFactory.getLogger(SchemaBasedMultitenantConnectionProvider.class);
	
	@Autowired
    private DataSource dataSource;
	
	private String databaseName;

	

	@Override
	public Connection getAnyConnection() throws SQLException {
		System.out.println("ANY CONNECTION  ");
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {		
		
		
		System.out.println("GET CONNECTION  "+tenantIdentifier);
		if(logger.isInfoEnabled()){
			logger.info("TENANT ID {} ",tenantIdentifier);
		}
        final Connection connection = getAnyConnection();
        try {
            if (tenantIdentifier != null) {
            	switchDatabase(tenantIdentifier,connection);
               
            }
        }
        catch ( SQLException e ) {
            throw new HibernateException("Problem setting schema to " + tenantIdentifier,e);
        }
        return connection;
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		
		System.out.println("RELEASE ANY  CONNECTION  ");
		connection.close();
		
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		  
		
		System.out.println("RELEASE   CONNECTION  ");
		connection.close();
		
	}

	@Override
	public boolean supportsAggressiveRelease() {
		if(logger.isInfoEnabled()){
			logger.info("Supports Aggressive release");
		}
		return true;
	}


	
	@Override
	public boolean isUnwrappableAs(Class arg0) {
		
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		
		try {
			return dataSource.unwrap(arg0);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	@Override
	public String getDatabaseName() {
		return databaseName;
	}
	
	
	
	
	
	

}
