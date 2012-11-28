package org.dexels.grus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.util.AuditLog;

public final class DbConnectionBroker extends Object
{
	protected String location, username, password;

	
	private final static Logger logger = LoggerFactory
			.getLogger(DbConnectionBroker.class);
	protected String dbIdentifier;
	protected Connection[] conns;
	protected boolean[] usedmap;
	protected long[] created;
	protected boolean[] aged;
	//private int minimum;    // Minimum number of connections (unused)
	protected int current;    // Current number of open connections
	protected int available;  // Number of available connections
	protected double timeoutDays;
	protected boolean closed; // Whether closed for business
	protected boolean dead = false;
	public  boolean supportsAutocommit = true;
	protected boolean sanityCheck = true;
	private static final AtomicInteger connectionCounter = new AtomicInteger();
	
	
	protected static final Map<Integer,DbConnectionBroker> transactionContextBrokerMap = Collections.synchronizedMap(new HashMap<Integer,DbConnectionBroker>());
	
	protected final void log(String message) {
		AuditLog.log("GRUS", Thread.currentThread().getName() + ": (url = " + location + ", user = " + username + ")" + message);
	}
	
	private static int getNextUniqueInteger() {
		return connectionCounter.incrementAndGet();
	}
	
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
		//minimum   = minConns;
		current   = 0;
		available = 0;
		timeoutDays = maxConnTime;
		
		if ( timeoutDays == 0 ) {
			maxConns = 1;
		}
		
		conns   = new Connection[maxConns];
		usedmap = new boolean[maxConns];
		created = new long[maxConns];
		aged    = new boolean[maxConns];
		for(int i=0; i<conns.length; i++) {
			conns[i]    = null;
			usedmap[i]  = false;
			aged[i]     = false;
			created[i]  = 0;
		}
		closed = false;
		
		if ( timeoutDays > 0 ) {
			
			Connection con = getConnection();
			freeConnection(con);
			GrusManager.getInstance().addBroker(this);
			
            log("Started new connectionbroker thread for: " + dbDriver + "/" + dbServer + "/" + dbLogin + ", refresh = " + timeoutDays);
		}
	}
	
	private final boolean testConnection(final Connection conn) {

		if ( conn == null ) {
			log("TESTCONNECTION: CONN = NULL...");
			return false;	
		}
		
		try {
			if(conn.isClosed()) {
				log("TESTCONNECTION: CONN WAS CLOSED...");
				return false;
			}

			if ( sanityCheck ) {
				// Check if it is the proper connection...

				String metaUsername = conn.getMetaData().getUserName();
				
				// MySql fix: My sql will add @localhost after the username, which confuses this test.
				// It won't reuse connections because this test always fails.
				if(metaUsername.indexOf("@")!=-1) {
					metaUsername = metaUsername.split("@")[0];
				}
				String metaLocation = conn.getMetaData().getURL();
				if ( !metaUsername.toLowerCase().equals(this.username.toLowerCase()) ||
						!metaLocation.toLowerCase().equals(this.location.toLowerCase())) {
					try {
						logger.warn("FOUND ILLEGAL CONNECTION: ");
						AuditLog.log("GRUS", "Found ILLEGAL connection " + metaLocation+"/"+metaUsername +
								", EXPECTED: " + this.location + "/" + this.username);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return false;
				}
			}
		} catch(Exception e) {
			log("TESTCONNECTION: CONN HAD EXCEPTION: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * This method return a Connection object based on a connection id (connection hashcode).
	 * 
	 * @param connectionId
	 * @return
	 */
	public static synchronized  Connection getConnection(int connectionId) {
		
		// Fetch proper broker.
		DbConnectionBroker broker = transactionContextBrokerMap.get(connectionId);
		if ( broker == null ) {
			logger.error("COULD NOT FIND BROKER FOR CONNECTION ID: " + connectionId);
			return null;
		}
		for ( int i = 0; i < broker.conns.length; i++ ) {
			if ( broker.conns[i] != null && broker.conns[i].hashCode() == connectionId ) {
				if ( broker.usedmap[i] )  {
					return broker.conns[i];
				} else {
					logger.error("Trying to get unused connection: " + connectionId);
					return null;
				}
			}
		}
		logger.error("Could not find connectionid: " + connectionId);
		return null;
	}
	
	/** 
	 * Fetch DbConnectionBroker based on connectionId
	 * 
	 * @param connectionId
	 * @return
	 */
	public static DbConnectionBroker getConnectionBroker(int connectionId) {
		
		DbConnectionBroker broker = transactionContextBrokerMap.get(connectionId);
		if ( broker == null ) {
			logger.warn("COULD NOT FIND BROKER FOR CONNECTION ID: " + connectionId);
			return null;
		}
		return broker;
	}
	
	public final synchronized void refreshConnections() {
		
		long maxAge = (long) (System.currentTimeMillis() - this.timeoutDays * 86400000L);
		// Check IDLE time, created[i] contains timestamp of last use.
		
		if ( this.conns != null ) {
			for (int i = 0; i < this.conns.length; i++) {
				if (this.conns[i] != null && this.created[i] < maxAge) {
					this.aged[i] = true;
				}
			}
		}
		
	}
	
	public final synchronized Connection getConnection() {
		if(closed && timeoutDays > 0) {
			return null;
		}

		// EDIT BY FRANK: Placed wait into a loop. Also added timeout to loop, to be sure
		if (timeoutDays > 0 && available == 0 && current == conns.length ) {
			try {
				wait(60000);
			} catch(InterruptedException e) {
				// dunno.
				e.printStackTrace();
			}
			if ( available == 0 && current == conns.length ) {
				return null;
			}
		}

		if(closed && timeoutDays > 0) {
			return null;
		}

		// Check for available existing connection.
		for(int i=0; i<conns.length; i++) {
			if(conns[i] != null && usedmap[i] == false) {
				// Test connection and check whether connection has not yet aged.
				--available;
				if(testConnection(conns[i]) && !aged[i]) {
					usedmap[i] = true;
					return conns[i];
				} else {
					try {
						if ( conns[i] != null ) {
							try {
								--current;
								conns[i].close();
							} catch (Throwable t) {
								logger.error("Error: ", t);
							}
							logger.warn("Removing aged connection: " + conns[i].hashCode());
							transactionContextBrokerMap.remove(conns[i].hashCode());
						}
					} catch (Throwable e) {
						logger.error("Error: ", e);
					}
					conns[i] = null;
					usedmap[i] = false;
				}
			}
		}
		
		// Create new connection if maxconnections has not yet been reached.
		for(int i=0; i<conns.length; i++) {
			if( conns[i] == null ) {
				try {
					//long start = System.currentTimeMillis();
					DriverManager.setLoginTimeout(5);
					Connection c = DriverManager.getConnection(location,username,password);
					while ( isDoubleEntry(c) ) {
						logger.error("Overlapping hashcode for connection found, trying new one.");
						c.close();
						c = DriverManager.getConnection(location,username,password);
					}
					conns[i] = c;
					
				} catch(SQLException e) {
					logger.error("SQL login failed",e);
					return null;
				}
				usedmap[i] = true;
				aged[i] = false;
				created[i] = System.currentTimeMillis();
				++current;
				transactionContextBrokerMap.put(conns[i].hashCode(), this);
				return conns[i];
			}
		}
//         "Assertion failure: no connections, retrying...");
//		return getConnection();
		log("@@@@@ RETURNING NULL!! THIS SHOULD NOT HAPPEN");
		return null;
	}
	
	public final synchronized String freeConnection(Connection conn) {
		
		int id = idOfConnection(conn);
		
		if( id >= 0 && usedmap[id] ) {
			usedmap[id] = false;
			++available;
			notify();
		}
		
		return null; // Duh?
	}
	
	private boolean isDoubleEntry(Connection con) {
		
		for(int i=0; i<conns.length; i++) {
			if ( conns[i] != null && conns[i] == con ) {
				return true;
			}
		}
		return false;
	}
	
	private final int idOfConnection(Connection conn) {
		for(int i=0; i<conns.length; i++) {
			if(conns[i] != null && conns[i] == conn) {
				return i;
			}
		}
		return -1;
	}
	
	private final void destroy(int millis) throws SQLException
	{
		SQLException ex = null;
		
		log("In destroy, millis: " + millis);
		closed = true;
		dead = true;
		
		GrusManager.getInstance().removeBroker(this);
		
		for(int i=0; i<conns.length; i++) {
			try {
				if(conns[i] != null) {
					conns[i].close();
					transactionContextBrokerMap.remove(conns[i].hashCode());
					log("Closed connection due to destroy: " + conns[i].hashCode());
					conns[i] = null;
					usedmap[i] = false;
				}
			} catch(SQLException e) {
				ex = e;
			}
		}
		if(ex != null) {
			throw ex;
		}
		
	}
	
	public synchronized void destroy() {
		try { destroy(2000); } catch(SQLException e) { }
	}
	
	public final int getUseCount() {
		return current - available;
	}
	
	public final int getSize() {
		return current;
	}
	
	public final int getMaxCount() {
		return conns.length;
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
		for (int i = 0; i < aged.length; i++) {
			aged[i] = true;
		}
	}

	public final static int getInstances() {
		return GrusManager.getInstance().getInstances();
	}
}
