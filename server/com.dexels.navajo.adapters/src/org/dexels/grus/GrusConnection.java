package org.dexels.grus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrusConnection {

	private final static Logger logger = LoggerFactory.getLogger(DbConnectionBroker.class);
	private final static int LOGIN_TIMEOUT = 10;
	private final static Random randomGen = new Random();
	
	final Connection myConnection;
	final DbConnectionBroker myBroker;
	final long id;
	final long created;
	final int maxAge;
	
	boolean aged = false;
	
	private static final AtomicInteger connectionCounter = new AtomicInteger();
	private static final Map<Long,GrusConnection> registeredConnections = new ConcurrentHashMap<Long, GrusConnection>();
	
	/**
	 * InstanceId is used to uniquely define a GrusConnection instance that is in use.
	 * It can be used to make sure that a GrusConnection is not freed twice.
	 */
	long instanceId;
	
	public long getInstanceId() {
		return instanceId;
	}

	protected long setInstanceId(long instanceId) {
		this.instanceId = instanceId;
		return instanceId;
	}

	@Deprecated
	private static final Map<Connection,GrusConnection> connectionMapping = new ConcurrentHashMap<Connection, GrusConnection>();
	
	public GrusConnection(String location, String username, String password, DbConnectionBroker broker, double maxAge) {
		DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
		try {
			myConnection = DriverManager.getConnection(location, username, password);
		} catch (SQLException e) {
			throw new InstantiationError("Could not create GrusConnection: " + e.getMessage());
		}
		myBroker = broker;
		id = connectionCounter.getAndIncrement();
		
		// setting max age, including a random element (mean of 1 day, stddev of 1 hour) to prevent massive 
		// connection refreshing at same moment
		double randomAge = randomGen.nextGaussian() * (3600000 * maxAge) + (86400000 * maxAge);	
		this.maxAge = (int) ( maxAge * randomAge);
		created = System.currentTimeMillis();
		registeredConnections.put(id, this);
		connectionMapping.put(myConnection, this);
		logger.info("Created new GrusConnection {}/{}@{}, for Connection: {} with maxage {}", id, username, location, myConnection, this.maxAge );
	}
	
	public Connection getConnection() {
		return myConnection;
	}
	
	public void setAged() {
		aged = ( System.currentTimeMillis() - created ) > maxAge;
	}
	
	public boolean isAged() {
		return aged;
	}
	
	public void setAgedForced() {
		aged = true;
	}
	
	public void destroy() {
		try {
			if ( myConnection != null ) {
				try {
					myConnection.close();
				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
				}
			}
			registeredConnections.remove(id);
			connectionMapping.remove(myConnection);
		} catch (Throwable t) {}
	}
	
	@Override
	public void finalize() {
		destroy();
	}
	
	public long getId() {
		return id;
	}
	
	@Deprecated
	public static GrusConnection getGrusConnectionByConnection(Connection conn) {
		return connectionMapping.get(conn);
	}
	
	public static GrusConnection getGrusConnectionById(long id) {
		return registeredConnections.get(id);
	}
	
	public DbConnectionBroker getMyBroker() {
		return  myBroker;
	}
	
}
