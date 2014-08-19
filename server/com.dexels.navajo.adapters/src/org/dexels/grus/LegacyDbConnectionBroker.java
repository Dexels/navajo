package org.dexels.grus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public final class LegacyDbConnectionBroker implements DbConnectionBroker
{
	protected String location, username, password;
	
	private final static Logger logger = LoggerFactory.getLogger(LegacyDbConnectionBroker.class);
	
	protected String dbIdentifier;
	
	protected int maxConnections;
	protected double timeoutDays;
	protected boolean dead = false;
	public  boolean supportsAutocommit = true;
	
	// Following set is used to hold unique GrusConnection instance id's that have been made available.
	protected final Set<Long> usedConnectionInstanceIds;
	private long instanceCounter = -1;
	
	// Following data structures are used to keep track of available GrusConnection objects and GrusConnections objects
	// that are in use.
	protected final Deque<GrusConnection> availableConnectionsStack;
	protected final Set<GrusConnection> inUse = Collections.newSetFromMap(new ConcurrentHashMap<GrusConnection, Boolean>());
	
	// The cardinality of this semaphore equals the maximum number of connections that this data source allows.
	private final Semaphore availableConnections;


	public LegacyDbConnectionBroker(String dbDriver,
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

	
	public LegacyDbConnectionBroker(String dbDriver,
			String dbServer,
			String dbLogin,
			String dbPassword,
			int minConns,
			int maxConns,
			double maxConnTime)
	throws ClassNotFoundException
	{
		if(dbDriver!=null) {
			Class.forName(dbDriver);
		}
		
		location  = dbServer;
		username  = dbLogin;
		password  = dbPassword;
		maxConnections = maxConns;
		timeoutDays = maxConnTime;
		
		if ( timeoutDays == 0 ) {
			maxConns = 1;
		}

		availableConnectionsStack = new ArrayDeque<GrusConnection>(maxConnections);
		usedConnectionInstanceIds = new HashSet<Long>();
		
		availableConnections = new Semaphore(maxConnections - 1);
		
		if ( timeoutDays > 0 ) {
			GrusManager.getInstance().addBroker(this);
            logger.info("Started new connectionbroker for: " + dbDriver + "/" + dbServer + "/" + dbLogin + ", refresh = " + timeoutDays);
		}
	}
	
	public static GrusConnection getGrusConnection(int connectionId) {

		GrusConnection gc = LegacyGrusConnection.getGrusConnectionById(connectionId);
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
	@Override
	public final Connection getConnection() throws SQLException {
		GrusConnection gc = getGrusConnection();
		if ( gc != null ) {
			return gc.getConnection();
		} else {
			return null;
		}
	}
	
	@Override
	public final GrusConnection getGrusConnection() {

		// Try to acquire connection. If not available block
		try {
			availableConnections.acquire();
		} catch (InterruptedException e1) {
			logger.debug("Thread interrupted",e1);
		}

		synchronized (this ) {

			if ( availableConnectionsStack.size() > 0 ) {
				GrusConnection gc = availableConnectionsStack.pop();
				if ( gc != null ) {
					boolean isClosed = false;
					try {
						isClosed = ( gc.getConnection() == null || gc.getConnection().isClosed() );
					} catch (Exception e) {
						isClosed = true;
					}
					if ( !gc.isAged() && !isClosed ) {
						inUse.add(gc);
						usedConnectionInstanceIds.add(gc.setInstanceId(instanceCounter++));
						return gc;
					} else {
						logger.info("Destroying GrusConnection " + gc.getId() + " due to " + ( isClosed ? "closed connection." : "old age."));
						gc.destroy();
					}
				}
			}
			
			if ( getSize() < maxConnections ) {
				try {
					LegacyGrusConnection gc = new LegacyGrusConnection(location, username, password, this, timeoutDays);
					inUse.add(gc);
					usedConnectionInstanceIds.add(gc.setInstanceId(instanceCounter++));
					return gc;
				} catch (Throwable e) {
					logger.error("Could not created connection: " + e.getMessage(), e);
					availableConnections.release();
				}
			}

			
		}

		return null;

	}
	
	/**
	 * Free a GrusConnection object for use by others.
	 * This method is resilient to the following unwanted situations:
	 * (1) If gc is null nothing happens
	 * (2) if gc is not in use and marked as available, nothing happens
	 * (3) if gc has been freed before, nothing happens.
	 * 
	 * @param gc
	 */
	@Override
	public final void freeConnection(GrusConnection gc) {

		if ( gc == null )
			return;

		boolean released = false;
		try {
			synchronized (this) {
				if (!inUse.remove(gc) ) {
					logger.warn("Attempting to free connection that is not in use: " + gc.getId() + ", is on stack: " + availableConnectionsStack.contains(gc));
					Thread.dumpStack();
					// If GrusConnection is not on the stack, destroy this 'illegal' connection.
					if ( !availableConnectionsStack.contains(gc) ) {
						gc.destroy();
						logger.error("Destroying connection that is both NOT in use and NOT and the available connections stack. THIS SHOULD NOT HAPPEN!");
					}
				} else {
					if ( usedConnectionInstanceIds.contains(gc.getInstanceId()) ) {
						released = true;
						usedConnectionInstanceIds.remove(gc.getInstanceId());
						availableConnectionsStack.push(gc);
					} else {
						logger.warn("Attempting to free connection that was already freed: " + gc.getInstanceId());
						Thread.dumpStack();
					}
				}
			}
		} finally {
			if ( released ) {
				availableConnections.release();
			}
		}
	}
	
	@Deprecated
	@Override
	public final void freeConnection(Connection conn) {

		logger.warn("In freeConnection(Connection)");
		
		GrusConnection gc = LegacyGrusConnection.getGrusConnectionByConnection(conn);
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
	
	@Override
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
	
	@Override
	public final int getUseCount() {
		return inUse.size();
	}
	
	@Override
	public final int getSize() {
		return availableConnectionsStack.size() + inUse.size();
	}
	
	@Override
	public final int getMaxCount() {
		return maxConnections;
	}
	
	@Override
	public final String getDbIdentifier() {
		return dbIdentifier;
	}
	
	@Override
	public final void setDbIdentifier(String dbIdentifier) {
		this.dbIdentifier = dbIdentifier;
	}
	
	@Override
	public final boolean isDead() {
		return dead;
	}
	
	@Override
	public void finalize() {
		destroy();
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean hasAutoCommit() {
		return supportsAutocommit;
	}
	
	@Override
	public void setSupportsAutoCommit(boolean b) {
		supportsAutocommit = b;
	}
	
	/**
	 * Age all connections, forcing creation of new connection.
	 * 
	 */
	@Override
	public void setCloseAll() {
		for ( GrusConnection gc: availableConnectionsStack ) {
			gc.setAgedForced();
		}
	}

	public final static int getInstances() {
		return GrusManager.getInstance().getInstances();
	}

}
