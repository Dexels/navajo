/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.dexels.grus;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnectionBroker {

	public String getUsername();

	public Connection getConnection() throws SQLException;

	public boolean hasAutoCommit();

	public int getMaxCount();

	public boolean isDead();

	public void destroy();

	public int getUseCount();

	public void freeConnection(GrusConnection gc);

	public GrusConnection getGrusConnection();

	public String getDbIdentifier();

	public void setDbIdentifier(String dbIdentifier);

	public void setSupportsAutoCommit(boolean b);

	public int getSize();

	public void freeConnection(Connection conn);

	public void setCloseAll();

}
