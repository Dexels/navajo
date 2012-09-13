/*
  Copyright (c) 2011, Christoph Läubrich. All rights reserved.

  The MySQLJDBCDataSourceService is licensed under the same terms as the MySQL Connector/J
  which are the terms of the GPLv2  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

*/
package com.oracle.jdbc.service.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.xa.client.OracleXADataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OracleJDBCDataSourceService implements DataSourceFactory {

	
	private final static Logger logger = LoggerFactory
			.getLogger(OracleJDBCDataSourceService.class);
	private ObjectPool pool;
	@SuppressWarnings("unused")
	private PoolableConnectionFactory poolableConnectionFactory;
    public void start() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //Load driver if not already done...
        Class<?> clazz = Class.forName("oracle.jdbc.OracleDriver");
        clazz.newInstance();
    }

    @Override
    public DataSource createDataSource(Properties props) throws SQLException {
//    	OracleConnectionPoolDataSource source = new OracleConnectionPoolDataSource();
    	OracleConnectionPoolDataSource source = new OracleConnectionPoolDataSource();
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
    	OracleConnectionPoolDataSource source = new OracleConnectionPoolDataSource();
        try {
			return (ConnectionPoolDataSource) setup(source, props);
		} catch (Exception e) {
			throw new SQLException("Trouble createPoolDataSource:",e);
		}
    }

    @Override
    public XADataSource createXADataSource(Properties props) throws SQLException {
//    	OracleConnectionPoolDataSource base = new OracleConnectionPoolDataSource();
    	OracleXADataSource source;
		try {
			source = setupXSource(props);
	        return source;
		} catch (Exception e) {
			throw new SQLException("Error creating XADatasource: ",e);
		}
    }

    @Override
    public Driver createDriver(Properties props) throws SQLException {
    	OracleDriver driver = new OracleDriver();
        //Any setup neccessary?
        return driver;
    }

    /**
     * Setups the basic properties for {@link DataSource}s
     * @throws Exception 
     */
    private DataSource setup(OracleConnectionPoolDataSource base, Properties props) throws Exception {
//    	 Oracle settings: {service.pid=navajo.resource.oracle-1332524829528-5, user=knvbkern, url=jdbc:oracle:thin:@10.0.0.1:1521:1521:aardnoot, service.factoryPid=navajo.resource.oracle, password=knvb, name=navajo.resource.default, maxPoolSize=10, initialPoolSize=10}
//
    	if (props == null) {
            return null;
        }
//        logger.info("Oracle settings: "+props);
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
        	base.setURL(props.getProperty(JDBC_URL));
        }
        if (props.containsKey(JDBC_USER)) {
        	base.setUser(props.getProperty(JDBC_USER));
        }
        DataSource source = createPooledConnection(base, (String)props.get(JDBC_URL),(String) props.get(JDBC_USER), (String)props.get(JDBC_PASSWORD),  (Integer)props.get(JDBC_MIN_POOL_SIZE),  (Integer)props.get(JDBC_MAX_POOL_SIZE));

    	return source;

    }

    /**
     * Setup the basic and extended properties for {@link XADataSource}s and
     * {@link ConnectionPoolDataSource}s
     * @throws Exception 
     */
    private OracleXADataSource setupXSource( Properties props) throws Exception {
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
   public DataSource createPooledConnection(final DataSource baseSource, String connectURI, 
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
	   
	   final GenericObjectPool connectionPool = new GenericObjectPool(null);

       
       if(minIdle!=null) {
           connectionPool.setMinIdle( minIdle );
       } else {
           connectionPool.setMinIdle( 2 );
    	   
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
       poolableConnectionFactory = new PoolableConnectionFactory(
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
