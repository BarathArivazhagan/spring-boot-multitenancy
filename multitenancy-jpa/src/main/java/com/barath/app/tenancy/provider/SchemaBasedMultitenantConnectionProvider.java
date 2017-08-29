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
	
	private DatabaseMetaData metadata;

	

	@Override
	public Connection getAnyConnection() throws SQLException {
		metadata=dataSource.getConnection().getMetaData();
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {		
		
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
		connection.close();
		
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		  connection.close();
		
	}

	@Override
	public boolean supportsAggressiveRelease() {
		
		return false;
	}

	@Override
	protected String getDatabaseName() {
		
		try {		
			if( metadata ==null){
				getAnyConnection();
			}
			return metadata.getDatabaseProductName();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isUnwrappableAs(Class arg0) {
		
		try {
			return !dataSource.isWrapperFor(arg0);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
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
	
	

}
