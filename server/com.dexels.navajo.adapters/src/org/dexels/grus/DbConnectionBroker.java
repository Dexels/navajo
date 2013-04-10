package org.dexels.grus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DbConnectionBroker 
{
	protected String location, username, password;
	
	private final static Logger logger = LoggerFactory.getLogger(DbConnectionBroker.class);
	
	protected String dbIdentifier;
	
	protected int maxConnections;
	protected double timeoutDays;
	protected boolean dead = false;
	public  boolean supportsAutocommit = true;
	
	protected final Deque<GrusConnection> availableConnectionsStack;
	protected final Set<GrusConnection> inUse = Collections.newSetFromMap(new ConcurrentHashMap<GrusConnection, Boolean>());
	
	public DbConnectionBroker(String dbDriver,
			String dbServer,
			String dbLogin,
			String dbPassword)
	throws ClassNotFoundException
	{
		this(dbDriver, dbServer, dbLogin, dbPassword, 8, 8, 0.1);
	}
	public DbConnectionBroker(String dbDriver,
			String dbServer,
			String dbLogin,
			String dbPassword,
			int minConns,
			int maxConns,
			String logFileString,
			double maxConnTime)
	throws ClassNotFoundException
	{
		this(dbDriver, dbServer, dbLogin, dbPassword,
				minConns, maxConns, maxConnTime);
	}
	
	public DbConnectionBroker(String dbDriver,
			String dbServer,
			String dbLogin,
			String dbPassword,
			int minConns,
			int maxConns,
			double maxConnTime)
	throws ClassNotFoundException
	{
		Class.forName(dbDriver);
		
		location  = dbServer;
		username  = dbLogin;
		password  = dbPassword;
		maxConnections = maxConns;
		timeoutDays = maxConnTime;
		
		if ( timeoutDays == 0 ) {
			maxConns = 1;
		}

		availableConnectionsStack = new ArrayDeque<GrusConnection>(maxConnections);
		
		if ( timeoutDays > 0 ) {
			GrusManager.getInstance().addBroker(this);
            logger.info("Started new connectionbroker thread for: " + dbDriver + "/" + dbServer + "/" + dbLogin + ", refresh = " + timeoutDays);
		}
	}
	
	/**
	 * This method return a Connection object based on a connection id (connection hashcode).
	 * 
	 * @param connectionId
	 * @return
	 */
	public static Connection getConnection(int connectionId) {

		GrusConnection gc = GrusConnection.getGrusConnectionById(connectionId);
		if ( gc != null ) {
			return gc.getConnection();
		} else {
			logger.error("Could not find connection id: " + connectionId);
			return null;
		}
	}
	
	
	public final synchronized void refreshConnections() {
		
		for ( GrusConnection gc : availableConnectionsStack ) {
			gc.setAged();
		}
		
	}
	
	public final synchronized Connection getConnection() {

		if ( availableConnectionsStack.size() > 0 ) {
			GrusConnection gc = availableConnectionsStack.pop();
			if ( gc != null ) {
				if ( !gc.isAged() ) {
					inUse.add(gc);
					return gc.getConnection();
				} else {
					logger.info("Destroying GrusConnection " + gc.getId() + " due to old age.");
					gc.destroy();
				}
			}
		}

		if ( availableConnectionsStack.size() < maxConnections ) {
			try {
				GrusConnection gc = new GrusConnection(location, username, password, this, timeoutDays);
				inUse.add(gc);
				return gc.getConnection();
			} catch (Exception e) {
				logger.error("Could not created connection: " + e.getMessage(), e);
			}
		} else {
			logger.error("No more database connections left");
		}
		
		return null;

	}
	
	public final synchronized void freeConnection(Connection conn) {

		GrusConnection gc = GrusConnection.getGrusConnectionByConnection(conn);
		
		if ( !inUse.contains(gc) ) {
			logger.warn("Freeing connection that is not in use..");
		}
		if ( gc == null ) {
			logger.error("Could not find GrusConnection for Connection: " + conn);
			if ( conn != null ) {
				try {
					logger.warn("Closing Connection anyway...");
					conn.close();
				} catch (SQLException e) {
				}
			}
		} else {
			inUse.remove(gc);
			availableConnectionsStack.push(gc);
		}

	}
	
	public synchronized void destroy() {
		logger.warn(location + "/" + username + ": In destroy()");
		dead = true;
		GrusManager.getInstance().removeBroker(this);
		for ( GrusConnection gc: availableConnectionsStack ) {
			gc.destroy();
		}
		availableConnectionsStack.clear();
		inUse.clear();
	}
	
	public final int getUseCount() {
		return inUse.size();
	}
	
	public final int getSize() {
		return availableConnectionsStack.size() + inUse.size();
	}
	
	public final int getMaxCount() {
		return maxConnections;
	}
	
	public final String getDbIdentifier() {
		return dbIdentifier;
	}
	
	public final void setDbIdentifier(String dbIdentifier) {
		this.dbIdentifier = dbIdentifier;
	}
	
	public final boolean isDead() {
		return dead;
	}
	
	public void finalize() {
		destroy();
	}
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Age all connections, forcing creation of new connection.
	 * 
	 */
	public void setCloseAll() {
		for ( GrusConnection gc: availableConnectionsStack ) {
			gc.setAgedForced();
		}
	}

	public final static int getInstances() {
		return GrusManager.getInstance().getInstances();
	}

	public static DbConnectionBroker getConnectionBroker(int transactionContext) {
		return GrusConnection.getGrusConnectionById(transactionContext).getMyBroker();
	}

	public static int getConnectionId(Connection con) {
		return (int) GrusConnection.getGrusConnectionByConnection(con).getId();
	}
}
