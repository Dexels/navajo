package com.postgresql.jdbc.service.impl;

import java.net.URI;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;








import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.osgi.service.jdbc.DataSourceFactory;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.common.BaseDataSource;
import org.postgresql.xa.PGXADataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PostgreSQLJDBCDataSourceService implements DataSourceFactory {

	
	private final static Logger logger = LoggerFactory
			.getLogger(PostgreSQLJDBCDataSourceService.class);
	private ObjectPool<Object> pool;
	
//	private PoolableConnectionFactory poolableConnectionFactory;
    public void start() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //Load driver if not already done...
        Class<?> clazz = Class.forName("org.postgresql.Driver");
        clazz.newInstance();
    }

    @Override
    public DataSource createDataSource(Properties props) throws SQLException {
    	PGConnectionPoolDataSource source = new PGConnectionPoolDataSource();
        try {
            DataSource result = setup(source, props);
            return result;
		} catch (Exception e) {
			throw new SQLException("Trouble createPoolDataSource:",e);
		}

        //        OracleConnectionCacheImpl.
    
    }

    @Override
    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
    	PGConnectionPoolDataSource source = new PGConnectionPoolDataSource();
        try {
			return (ConnectionPoolDataSource) setup(source, props);
		} catch (Exception e) {
			throw new SQLException("Trouble createPoolDataSource:",e);
		}
    }

    @Override
    public XADataSource createXADataSource(Properties props) throws SQLException {
//    	OracleConnectionPoolDataSource base = new OracleConnectionPoolDataSource();
    	PGXADataSource source;
		try {
			source = setupXSource(props);
	        return source;
		} catch (Exception e) {
			throw new SQLException("Error creating XADatasource: ",e);
		}
    }

    @Override
    public Driver createDriver(Properties props) throws SQLException {
    	org.postgresql.Driver driver = new org.postgresql.Driver();
        //Any setup neccessary?
        return driver;
    }

    /**
     * Setups the basic properties for {@link DataSource}s
     * @throws Exception 
     */
    public DataSource setup(PGConnectionPoolDataSource base, Properties props) throws Exception {
    	if (props == null) {
            return null;
        }
        if (props.containsKey(JDBC_DATABASE_NAME)) {
        	base.setDatabaseName(props.getProperty(JDBC_DATABASE_NAME));
        }
        if (props.containsKey(JDBC_DATASOURCE_NAME)) {
            //not supported?
        }
        if (props.containsKey(JDBC_DESCRIPTION)) {
            //not suported?
        }
        if (props.containsKey(JDBC_NETWORK_PROTOCOL)) {
            //not supported?
        }
        if (props.containsKey(JDBC_PASSWORD)) {
        	base.setPassword(props.getProperty(JDBC_PASSWORD));
        }
        if (props.containsKey(JDBC_PORT_NUMBER)) {
        	base.setPortNumber(Integer.parseInt(props.getProperty(JDBC_PORT_NUMBER)));
        }
        if (props.containsKey(JDBC_ROLE_NAME)) {
            //not supported?
        }
        if (props.containsKey(JDBC_SERVER_NAME)) {
        	base.setServerName(props.getProperty(JDBC_SERVER_NAME));
        }
        if (props.containsKey(JDBC_URL)) {       	
        	// PGConnectionPoolDataSource has no support for a JDBC url..
        	//String url = "jdbc:postgres://hostname:432/dbname;collation=abc:PRIMARY";
        	String cleanURI = props.getProperty(JDBC_URL).substring(5);
        	URI uri = URI.create(cleanURI);
        	String dbName = (uri.getPath().split(";")[0]).substring(1);

        	base.setServerName(uri.getHost());
        	base.setPortNumber(uri.getPort());
        	base.setDatabaseName(dbName);
       }
        if (props.containsKey(JDBC_USER)) {
        	base.setUser(props.getProperty(JDBC_USER));
        }
        DataSource source = createPooledConnection(base, (String)props.get(JDBC_URL),(String) props.get(JDBC_USER), (String)props.get(JDBC_PASSWORD),  (Integer)props.get("min_connections"),  (Integer)props.get("max_connections"));
        
    	return source;

    }

    /**
     * Setup the basic and extended properties for {@link XADataSource}s and
     * {@link ConnectionPoolDataSource}s
     * @throws Exception 
     */
    private PGXADataSource setupXSource( Properties props) throws Exception {
        if (props == null) {
            return null;
        }
        return null; 
    }
    
    /**
    *
    * @param connectURI - JDBC Connection URI
    * @param username - JDBC Connection username
    * @param password - JDBC Connection password
    * @param minIdle - Minimum number of idel connection in the connection pool
    * @param maxActive - Connection Pool Maximum Capacity (Size)
    * @throws Exception
    */
   public DataSource createPooledConnection(final BaseDataSource baseSource, String connectURI, 
	final String username, 
	final String password,
	Integer minIdle, Integer maxActive
	) throws Exception {
       //
       // First, we'll need a ObjectPool that serves as the
       // actual pool of connections.
       //
       // We'll use a GenericObjectPool instance, although
       // any ObjectPool implementation will suffice.
       //
	   
	   final GenericObjectPool<Object> connectionPool = new GenericObjectPool<Object>(null);

       
       if(minIdle!=null) {
           connectionPool.setMinIdle( minIdle );
       } else {
           connectionPool.setMinIdle( 5 );
    	   
       }
       if(maxActive!=null) {
           connectionPool.setMaxActive( maxActive );
       } else {
           connectionPool.setMaxActive( 5 );
       }

     this.pool = connectionPool; 

       
     ConnectionFactory connectionFactory = new ConnectionFactory() {
		
		@Override
		public Connection createConnection() throws SQLException {

			return baseSource.getConnection(username,password);
		}
	};
	new PoolableConnectionFactory(
     	connectionFactory,connectionPool,null,"select 1 from dual",false,false);

       PoolingDataSource dataSource = 
       	new PoolingDataSource(connectionPool);

       return dataSource;
   }
   
   public void printDriverStats() throws Exception {
       logger.info("NumActive: " + pool.getNumActive());
       logger.info("NumIdle: " + pool.getNumIdle());
   }


}
