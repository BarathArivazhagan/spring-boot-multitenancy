package com.barath.app.tenancy.provider;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;


/**
 * An Abstract class used to switch between the schemas based on tenant identifier.
 * 
 * @author barath.arivazhagan
 *
 */
public abstract class AbstractDatabaseSwitchingDataSource {
	
	public static enum Language {
		HSQL("SET SCHEMA ", ""), MYSQL("USE ", "`"), ORACLE("ALTER SESSION SET CURRENT_SCHEMA=", "\"");

		private final String switchCommand;
		private final String quoteChar;

		private Language(String switchCommand, String quoteChar) {
			this.switchCommand = switchCommand;
			this.quoteChar = quoteChar;
		}

		public String switchDatabase(String dbName) {
			String query = switchCommand + quoteChar + dbName + quoteChar; 
			if(this.name().equals(ORACLE.name())) {
				return query;
			}
			else {
				return query + ";";
			}
		}
	};

	protected DataSource wrappedDataSource;
	protected Language language = Language.MYSQL;

	public void setLanguage(Language l) {
		this.language = l;
	}
	
	
	protected Connection switchDatabase(String tenantId,Connection con) throws SQLException {
		String databaseName = getDatabaseName();
		if (databaseName != null) {
			Statement s = con.createStatement();
			try {
				s.execute(getLanguage(databaseName).switchDatabase(tenantId));
			} catch (SQLException e) { 
				con.close();
				throw e;
			} finally {
				s.close();
			}
		}
		return con;
	}

	abstract protected String getDatabaseName();
	
	
	protected Language getLanguage(String databaseName){
		
		switch(databaseName.toUpperCase()){
			
			case "MYSQL" : return Language.MYSQL;
			
			case "HSQL" : return Language.HSQL;
			
			case "ORACLE" : return Language.ORACLE;
		
			default : return Language.MYSQL;
		}
		
	}


}
