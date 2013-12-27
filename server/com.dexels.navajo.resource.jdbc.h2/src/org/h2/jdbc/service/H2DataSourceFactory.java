/**
 * 
 */
package org.h2.jdbc.service;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.jdbc.DataSourceFactory;


public class H2DataSourceFactory implements DataSourceFactory {

    public void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("org.h2.Driver", true, H2DataSourceFactory.class.getClassLoader());
        clazz.newInstance();
    }

    /* (non-Javadoc)
     * @see org.osgi.service.jdbc.DataSourceFactory#createDataSource(java.util.Properties)
     */
    @Override
	public DataSource createDataSource(Properties props) throws SQLException {
        JdbcDataSource source = new JdbcDataSource();
        setup(source, props);
        return source;
    }

    /* (non-Javadoc)
     * @see org.osgi.service.jdbc.DataSourceFactory#createConnectionPoolDataSource(java.util.Properties)
     */
    @Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
        JdbcDataSource source = new JdbcDataSource();
        setupXSource(source, props);
        return source;
    }

    /* (non-Javadoc)
     * @see org.osgi.service.jdbc.DataSourceFactory#createXADataSource(java.util.Properties)
     */
    @Override
	public XADataSource createXADataSource(Properties props) throws SQLException {
        JdbcDataSource source = new JdbcDataSource();
        setupXSource(source, props);
        return source;
    }

    /* (non-Javadoc)
     * @see org.osgi.service.jdbc.DataSourceFactory#createDriver(java.util.Properties)
     */
    @Override
	public Driver createDriver(Properties props) throws SQLException {
        return new org.h2.Driver();
    }

    /**
     * Setups the basic properties for {@link DataSource}s
     */
    private void setup(JdbcDataSource source, Properties props) {
        if (props == null) {
            return;
        }
        if (props.containsKey(JDBC_PASSWORD)) {
            source.setPassword(props.getProperty(JDBC_PASSWORD));
        }
        if (props.containsKey(JDBC_PORT_NUMBER)) {
            //not supported?
        }
        if (props.containsKey(JDBC_ROLE_NAME)) {
            //not supported?
        }
        if (props.containsKey(JDBC_SERVER_NAME)) {
            //not supported?
        }
        if (props.containsKey(JDBC_URL)) {
            source.setURL(props.getProperty(JDBC_URL));
        }
        if (props.containsKey(JDBC_USER)) {
            source.setUser(props.getProperty(JDBC_USER));
        }
    }

    /**
     * Setup the basic and extended properties for {@link XADataSource}s and
     * {@link ConnectionPoolDataSource}s
     */
    private void setupXSource(JdbcDataSource source, Properties props) {
        if (props == null) {
            return;
        }
        setup(source, props);
        if (props.containsKey(JDBC_INITIAL_POOL_SIZE)) {
            //not supported?
        }
        if (props.containsKey(JDBC_MAX_IDLE_TIME)) {
            //not supported?
        }
        if (props.containsKey(JDBC_MAX_STATEMENTS)) {
            //not supported?
        }
        if (props.containsKey(JDBC_MAX_POOL_SIZE)) {
            //not supported?
        }
        if (props.containsKey(JDBC_MIN_POOL_SIZE)) {
            //not supported?
        }
    }

}
