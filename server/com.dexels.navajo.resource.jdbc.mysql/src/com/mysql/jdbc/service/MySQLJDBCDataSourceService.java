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
package com.mysql.jdbc.service;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * Implementation of the OSGi {@link DataSourceFactory} for MySQL, no special
 * properties are supported yet
 * 
 * @author Christoph Läubrich
 */
public class MySQLJDBCDataSourceService implements DataSourceFactory {

    public void start() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //Load driver if not already done...
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        // The newInstance() call is a work around for some
        // broken Java implementations, see MySQL Connector/J documentation
        clazz.newInstance();
    }

    @Override
    public DataSource createDataSource(Properties props) throws SQLException {
        MysqlDataSource source = new MysqlDataSource();
        setup(source, props);
        return source;
    }

    @Override
    public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
        MysqlConnectionPoolDataSource source = new MysqlConnectionPoolDataSource();
        setup(source, props);
        return source;
    }

    @Override
    public XADataSource createXADataSource(Properties props) throws SQLException {
        MysqlXADataSource source = new MysqlXADataSource();
        setupXSource(source, props);
        return source;
    }

    @Override
    public Driver createDriver(Properties props) throws SQLException {
        com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
        //Any setup neccessary?
        return driver;
    }

    /**
     * Setups the basic properties for {@link DataSource}s
     */
    private void setup(MysqlDataSource source, Properties props) {
        if (props == null) {
            return;
        }
        if (props.containsKey(JDBC_DATABASE_NAME)) {
            source.setDatabaseName(props.getProperty(JDBC_DATABASE_NAME));
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
            source.setPassword(props.getProperty(JDBC_PASSWORD));
        }
        if (props.containsKey(JDBC_PORT_NUMBER)) {
            source.setPortNumber(Integer.parseInt(props.getProperty(JDBC_PORT_NUMBER)));
        }
        if (props.containsKey(JDBC_ROLE_NAME)) {
            //not supported?
        }
        if (props.containsKey(JDBC_SERVER_NAME)) {
            source.setServerName(props.getProperty(JDBC_SERVER_NAME));
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
    private void setupXSource(MysqlXADataSource source, Properties props) {
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
