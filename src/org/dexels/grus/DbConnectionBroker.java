package org.dexels.grus;

import java.sql.*;

import com.dexels.navajo.util.AuditLog;

public final class DbConnectionBroker extends Object
{
	protected String location, username, password;


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
	
	private static int instances = 0;
	
	protected final void log(String message) {
		AuditLog.log("GRUS", Thread.currentThread().getName() + ": (url = " + location + ", user = " + username + ")" + message);
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
		
		//System.err.println("in DBCONNECTIONBROKER(), FOUND JDBC DRIVER CLASS: " + dbDriver + ", LOCATION = " + dbServer);
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
			return false;
			
		}
		try {
			if(conn.isClosed()) {
				return false;
			}

			if ( sanityCheck ) {
				// Check if it is the proper connection...
				String metaUsername = conn.getMetaData().getUserName();
				String metaLocation = conn.getMetaData().getURL();
				if ( !metaUsername.toLowerCase().equals(this.username.toLowerCase()) ||
						!metaLocation.toLowerCase().equals(this.location.toLowerCase())) {
					try {
						conn.close();
						System.err.println("FOUND ILLEGAL CONNECTION.");
						AuditLog.log("GRUS", "Found ILLEGAL connection " + metaLocation+"/"+metaUsername +
								", EXPECTED: " + this.location + "/" + this.username);
					} catch (Exception e) {}
					return false;
				}
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	
	public final synchronized Connection getConnection() {
		//System.err.println("BrokerHash: +"+hashCode()+"+total connections: "+conns.length+" available: "+available+" current: "+current);

		if(closed && timeoutDays > 0) {
			log("Broker closed. No more connections available.");
			return null;
		}

		// EDIT BY FRANK: Placed wait into a loop. Also added timeout to loop, to be sure
		while(timeoutDays > 0 && available == 0 && current == conns.length ) {
			try {
				log("Waiting for connection " + username + "@" + location + " to become available. current = " + current);
				wait(10000);
			} catch(InterruptedException e) {
				// dunno.
			}
		}

		if(closed && timeoutDays > 0) {
			return null;
		}

		// Check for available existing connection.
		for(int i=0; i<conns.length; i++) {
			if(conns[i] != null && usedmap[i] == false) {
				--available;
				// Test connection and check whether connection has not yet aged.
				if(testConnection(conns[i]) && !aged[i]) {
					usedmap[i] = true;
					return conns[i];
				} else {
					log("Invalid connection, did not pass test: " + conns[i].hashCode());
					--current;
					try {
						if ( conns[i] != null ) {
							conns[i].close();
						}
					} catch (Throwable e) {
					}
					conns[i] = null;
					usedmap[i] = false;
				}
			}
		}
		
		// Create new connection if maxconnections has not yet been reached.
		for(int i=0; i<conns.length; i++) {
			if( conns[i] == null && usedmap[i] == false ) {
				try {
					//System.out.println("IN DBCONNECTION BROKER: CREATING NEW CONNECTION FOR " + username);
					//long start = System.currentTimeMillis();
					DriverManager.setLoginTimeout(5);
					conns[i] = DriverManager.getConnection(location,username,password);
					//System.err.println("Opening connection to " + username + " took: " + (  System.currentTimeMillis() - start ));
				} catch(SQLException e) {
					e.printStackTrace(System.err);
//					return getConnection();
					return null;
				}
				usedmap[i] = true;
				aged[i] = false;
				created[i] = System.currentTimeMillis();
				++current;
				return conns[i];
			}
		}
		// TODO PUT REQUEST IN WAITING LIST!
        log("Assertion failure: no connections, retrying...");
		return getConnection();
	}
	
	public final synchronized String freeConnection(Connection conn) {
		
		if ( timeoutDays == 0 ) {	
			try {
				//System.err.println("trying to destroy....");
				destroy(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		int id = idOfConnection(conn);
		if(id >= 0) {
			usedmap[id] = false;
			++available;
			//System.err.println("In Free: available: "+available);
			notifyAll();
		}
		
		return null; // Duh?
	}
	
	private final int idOfConnection(Connection conn) {
		for(int i=0; i<conns.length; i++)
			if(conns[i] == conn)
				return i;
		return -1;
	}
	
	private final synchronized void destroy(int millis) throws SQLException
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
					log("Closed connection due to destroy: " + conns[i].hashCode());
					conns[i] = null;
					usedmap[i] = false;
					//System.err.println(">>>>>> Closed connection.");
				}
			} catch(SQLException e) {
				ex = e;
			}
		}
		if(ex != null) {
			throw ex;
		}
		
	}
	
	public void destroy() {
		try { destroy(2000); } catch(SQLException e) { }
	}
	
	public final int getUseCount() {
		return current - available;
	}
	
	public final int getSize() {
		return current;
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
