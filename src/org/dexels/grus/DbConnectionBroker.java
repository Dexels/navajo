package org.dexels.grus;

import java.sql.*;

public final class DbConnectionBroker extends Object implements Runnable
{
	private String location, username, password;
	private String dbIdentifier;
	private Connection[] conns;
	private boolean[] usedmap;
	private long[] created;
	//private int minimum;    // Minimum number of connections (unused)
	private int current;    // Current number of open connections
	private int available;  // Number of available connections
	private double timeoutDays;
	private boolean closed; // Whether closed for business
	private Thread thread;
	private boolean dead = false;
	public  boolean supportsAutocommit = true;
	
	private static int instances = 0;
	private static long createdInstances = 0;
	
	private final void log(String message) {
		System.err.println("DBConnectionBroker (" + Thread.currentThread().getName() + "): " + message);
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
		for(int i=0; i<conns.length; i++) {
			conns[i]    = null;
			usedmap[i]  = false;
			created[i]  = 0;
		}
		closed = false;
		
		if ( timeoutDays > 0 ) {
			
			thread = new Thread(this, "Grus broker-" + ++createdInstances + ":" + dbServer + "/" + dbLogin );
			
			/** Set this thread to Deamon mode. If there are only deamon threads active, the vm will close.
			 *  Otherwise the vm will never close. Changed for the eclipse plugin*/
			thread.setDaemon(true);
			thread.start();
			Connection con = getConnection();
			freeConnection(con);
            log("Started new connectionbroker thread for: " + dbDriver + "/" + dbServer + "/" + dbLogin + ", refresh = " + timeoutDays);
		}
	}
	
	public void run() {
		long maxAge;
		instances++;

		try {
			while (!closed) {
				
				synchronized (this) {
					wait(10000);
					//System.err.println(Thread.currentThread().getName() + ": CHECKING IDLE CONNECTIONS");
					
					maxAge = (long) (System.currentTimeMillis() - timeoutDays * 86400000L);
					int idle = 0;
					int currentCount = current;
					//System.err.println(Thread.currentThread().getName() + ": MAXAGE IS: " + maxAge);
					// Check IDLE time, created[i] contains timestamp of last use.
					for (int i = 0; i < conns.length; i++) {
						if (conns[i] != null && !usedmap[i] && created[i] < maxAge) {
							try {
								log("Closing idle connection.");
								conns[i].close();
								idle++;
							} catch (SQLException e) {
								//e.printStackTrace(System.err);
							}
							conns[i] = null;
							usedmap[i] = false;
							--available;
							--current;
							// System.err.println("Checking timeout for
							// connections, current count is " + current);
						}
					}
					//System.err.println(Thread.currentThread().getName() + ": IDLE COUNT: " + idle + ", currentCount = " + currentCount);
					if (idle == currentCount) {
						log("Nobody interested anymore, about to kill thread ( idle = " + idle + ", currentCount = " + currentCount + ")");
						// Nobody interested anymore.
						closed = true;
						dead = true;
					}
				}
			}
		} catch (InterruptedException ie) {
			//
		}
		finally { 
			instances--;
		}
	    log("Killing thread.");
		closed = true;
		dead = true;
	}
	
	private final boolean testConnection(Connection conn) {
		try {
			if(conn.isClosed()) {
				return false;
			}
		} catch(SQLException e) {
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
				wait();
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
				if(testConnection(conns[i])) {
					// Reset created time(!)
					created[i] = System.currentTimeMillis();
					usedmap[i] = true;
					return conns[i];
				} else {
					--current;
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
	
	public final long getIdleTime(Connection conn) {
		int id = idOfConnection(conn);
		if(id >= 0) {
			return System.currentTimeMillis() - created[id];
		}
		return 0;
	}
	
	private final synchronized void destroy(int millis) throws SQLException
	{
		SQLException ex = null;
		
		log("In destroy, millis: " + millis);
		closed = true;
		dead = true;
		if ( timeoutDays > 0 ) {
			thread.interrupt();
			try { wait(millis); } catch(InterruptedException e) { }
		}
		for(int i=0; i<conns.length; i++) {
			try {
				if(conns[i] != null) {
					conns[i].close();
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
		try { destroy(10000); } catch(SQLException e) { }
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
	
	public final static int getInstances() {
		return instances;
	}
}
