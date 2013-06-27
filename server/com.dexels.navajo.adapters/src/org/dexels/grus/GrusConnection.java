package org.dexels.grus;

import java.sql.Connection;
import java.sql.SQLException;

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

}