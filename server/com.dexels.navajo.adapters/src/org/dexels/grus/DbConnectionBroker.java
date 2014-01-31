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
