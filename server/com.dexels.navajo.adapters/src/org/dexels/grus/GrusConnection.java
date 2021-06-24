/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.dexels.grus;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface GrusConnection {

	public long getInstanceId();

	public Connection getConnection() throws SQLException;

	public void setAged();

	public boolean isAged();

	public void setAgedForced();

	public void destroy();

	public long getId();

	public DbConnectionBroker getMyBroker();

	public long setInstanceId(long l);

	public void autocommit(boolean b);

	public void rollback(boolean b);

    DataSource getDatasource();


}