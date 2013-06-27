package org.dexels.grus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyGrusConnection implements GrusConnection {

	private final static Logger logger = LoggerFactory.getLogger(DbConnectionBroker.class);
	private final static int LOGIN_TIMEOUT = 10;
	
	final Connection myConnection;
	final DbConnectionBroker myBroker;
	final long id;
	final long created;
	final int maxAge;
	
	boolean aged = false;
	
	private static final AtomicInteger connectionCounter = new AtomicInteger();
	private static final Map<Long,LegacyGrusConnection> registeredConnections = new ConcurrentHashMap<Long, LegacyGrusConnection>();
	
	/**
	 * InstanceId is used to uniquely define a GrusConnection instance that is in use.
	 * It can be used to make sure that a GrusConnection is not freed twice.
	 */
	long instanceId;
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#getInstanceId()
	 */
	@Override
	public long getInstanceId() {
		return instanceId;
	}

	public long setInstanceId(long instanceId) {
		this.instanceId = instanceId;
		return instanceId;
	}

	@Deprecated
	private static final Map<Connection,GrusConnection> connectionMapping = new ConcurrentHashMap<Connection, GrusConnection>();
	
	public LegacyGrusConnection(String location, String username, String password, DbConnectionBroker broker, double maxAge) {
		DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
		try {
			myConnection = DriverManager.getConnection(location, username, password);
		} catch (SQLException e) {
			throw new InstantiationError("Could not create GrusConnection: " + e.getMessage());
		}
		myBroker = broker;
		id = connectionCounter.getAndIncrement();
		this.maxAge = (int) ( maxAge * 86400000L);
		created = System.currentTimeMillis();
		registeredConnections.put(id, this);
		connectionMapping.put(myConnection, this);
		logger.info("Created new GrusConnection " + id + "/" + username + "@" + location + ", for Connection: " + myConnection);
	}
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return myConnection;
	}
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#setAged()
	 */
	@Override
	public void setAged() {
		aged = ( System.currentTimeMillis() - created ) > maxAge;
	}
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#isAged()
	 */
	@Override
	public boolean isAged() {
		return aged;
	}
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#setAgedForced()
	 */
	@Override
	public void setAgedForced() {
		aged = true;
	}
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#destroy()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#finalize()
	 */
	@Override
	public void finalize() {
		destroy();
	}
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#getId()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see org.dexels.grus.GrusConnection#getMyBroker()
	 */
	@Override
	public DbConnectionBroker getMyBroker() {
		return  myBroker;
	}
}
