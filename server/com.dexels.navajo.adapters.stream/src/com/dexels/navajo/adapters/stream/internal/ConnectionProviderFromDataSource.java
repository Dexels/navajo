/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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