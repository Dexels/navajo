package org.dexels.grus;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

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
	private final Semaphore availableConnections;
	
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
		availableConnections = new Semaphore(maxConnections - 1);
		
		if ( timeoutDays > 0 ) {
			GrusManager.getInstance().addBroker(this);
            logger.info("Started new connectionbroker thread for: " + dbDriver + "/" + dbServer + "/" + dbLogin + ", refresh = " + timeoutDays);
		}
	}
	
	public static GrusConnection getGrusConnection(int connectionId) {

		GrusConnection gc = GrusConnection.getGrusConnectionById(connectionId);
		if ( gc != null ) {
			return gc;
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
	
	@Deprecated
	public final Connection getConnection() {
		GrusConnection gc = getGrusConnection();
		if ( gc != null ) {
			return gc.getConnection();
		} else {
			return null;
		}
	}
	
	public final GrusConnection getGrusConnection() {

		// Try to acquire connection. If not available block
		try {
			availableConnections.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		synchronized (this ) {

			if ( availableConnectionsStack.size() > 0 ) {
				GrusConnection gc = availableConnectionsStack.pop();
				if ( gc != null ) {
					boolean isClosed = false;
					try {
						isClosed = gc.getConnection().isClosed();
					} catch (Exception e) {
						isClosed = true;
					}
					if ( !gc.isAged() && gc.getConnection() != null && !isClosed ) {
						inUse.add(gc);
						return gc;
					} else {
						logger.info("Destroying GrusConnection " + gc.getId() + " due to " + ( isClosed ? " closed connection." : "old age."));
						gc.destroy();
					}
				}
			}
			
			if ( getSize() < maxConnections ) {
				try {
					GrusConnection gc = new GrusConnection(location, username, password, this, timeoutDays);
					inUse.add(gc);
					return gc;
				} catch (Throwable e) {
					logger.error("Could not created connection: " + e.getMessage(), e);
					availableConnections.release();
				}
			}

			
		}

		return null;

	}
	
	public final void freeConnection(GrusConnection gc) {

		if ( gc == null )
			return;

		boolean released = false;
		try {
			synchronized (this ) {
				if (!inUse.remove(gc) ) {
					logger.warn("Freeing connection that is not in use: " + gc.getId() + ", is on stack: " + availableConnectionsStack.contains(gc));
					Thread.dumpStack();
					// If GrusConnection is not on the stack, destroy this 'illegal' connection.
					if ( !availableConnectionsStack.contains(gc) ) {
						gc.destroy();
						logger.warn("Destroying connection that is both NOT in use and NOT and the available connections stack. THIS SHOULD NOT HAPPEN!");
					}
				} else {
					released = true;
					availableConnectionsStack.push(gc);
				}
			}
		} finally {
			if ( gc != null && released ) {
				availableConnections.release();
			}
		}
	}
	
	@Deprecated
	public final void freeConnection(Connection conn) {

		logger.warn("In freeConnection(Connection)");
		
		GrusConnection gc = GrusConnection.getGrusConnectionByConnection(conn);
		if ( gc != null ) {
			freeConnection(gc);
		} else {
			logger.error("Could not find GrusConnection for Connection: " + conn);
			if ( conn != null ) {
				try {
					logger.warn("Closing Connection anyway...");
					conn.close();
				} catch (Exception e) {
				}
			}
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

}
