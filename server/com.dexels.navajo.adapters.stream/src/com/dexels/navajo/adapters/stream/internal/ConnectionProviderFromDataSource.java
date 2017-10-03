package com.dexels.navajo.adapters.stream.internal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.exceptions.SQLRuntimeException;

/**
 * Provides database connections from a {@link DataSource}.
 */
public class ConnectionProviderFromDataSource implements ConnectionProvider {

    private final DataSource dataSource;

    /**
     * Constructor.
     * 
     * @param dataSource
     *            database connection source
     */
    public ConnectionProviderFromDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection get() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    @Override
    public void close() {
        // do nothing
    }

}