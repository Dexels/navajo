package com.dexels.connectionbroker;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionBroker {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionBroker.class);

	private final Map<String,DataSourceFactory> driverMap = new HashMap<String,DataSourceFactory>();
	
	
	public void activate() {
//		Dictionary properties = context.getProperties();
//			Enumeration en = properties.keys();
//			while(en.hasMoreElements()) {
//				String key = (String)en.nextElement();
//			}
	}
	
	public void registerDriver(DataSourceFactory df) {
		logger.debug("Registering driver: "+df.getClass()+" : "+DataSourceFactory.JDBC_DESCRIPTION);
		String name = df.getClass().getName();
		driverMap.put(name, df);
//		try {
//			test(df);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	public void unregisterDriver(DataSourceFactory df) {
		logger.info("Unregistering driver: "+df.getClass()+" : "+DataSourceFactory.JDBC_DESCRIPTION);
	}

	public ConnectionPoolDataSource getPooledConnection(String name, Properties settings) throws SQLException {
		DataSourceFactory dsf = driverMap.get(name);
		return dsf.createConnectionPoolDataSource(settings);
	}
	
//	public void test(DataSourceFactory factory) throws SQLException {
//			Properties prop = new Properties(); 
//		    prop.put(DataSourceFactory.JDBC_URL, "jdbc:h2:~/h2test"); 
//		    prop.put(DataSourceFactory.JDBC_USER, "sa"); 
//		    prop.put(DataSourceFactory.JDBC_PASSWORD, ""); 
//		    DataSource source = factory.createDataSource(prop); 
//		    Connection connection = source.getConnection(); 
//		    Statement stm = connection.createStatement(); 
////		  int o=  stm.executeUpdate("CREATE TABLE OEMPALOEMPA"); 
//		    ResultSet result = stm.executeQuery("SELECT * FROM OEMPALOEMPA"); 
//		    while (result.next()) { 
//		        System.out.println(result.getObject(1)); 
//		    } 
//		    result.close(); 
//		    stm.close(); 
//		    connection.close(); 
//	}
}
