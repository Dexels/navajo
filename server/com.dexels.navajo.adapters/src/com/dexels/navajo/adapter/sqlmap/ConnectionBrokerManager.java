package com.dexels.navajo.adapter.sqlmap;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dexels.grus.DbConnectionBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ResourceManager;
import com.dexels.navajo.server.resource.ServiceAvailability;


@SuppressWarnings({"unused"})
public class ConnectionBrokerManager extends Object implements ResourceManager, ConnectionBrokerManagerMBean {

  private Map<String,SQLMapBroker> brokerMap = Collections.synchronizedMap(new HashMap<String,SQLMapBroker>());
 
  private boolean debug = false;

  private static Object semaphore = new Object();
  
  private final static Logger logger = LoggerFactory
		.getLogger(ConnectionBrokerManager.class);

  public ConnectionBrokerManager() {
    super();
    JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "ConnectionBrokerManager");
  }

  public void terminate() {
	  try {
		for (SQLMapBroker sq : brokerMap.values()) {
			// close them or something?
		}
		  JMXHelper.deregisterMXBean( JMXHelper.NAVAJO_DOMAIN, "ConnectionBrokerManager");
		  brokerMap.clear();
	  } catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  
  /**
   * 
   * @param dsrc, datasource name
   * @param drv, JDBC driver name
   * @param url, JDBC url
   * @param usr, database username
   * @param pwd, database password
   * @param minconn, minimum number of connections
   * @param maxconn, maximum number of connections
   * @param lfile, log file name
   * @param rfrsh, refresh rate; if set to 0, set non-broker mode
   * @param ac, autocommit flag
   * @param forcecreation, if set to true don't search for exact or similar brokers, just create new one.
   * 
   * @throws ClassNotFoundException
   */
  public final void put(final String dsrc,
		  final String drv, final String url,
		  final String usr,
		  final String pwd, final int minconn,
		  final int maxconn,
		  final String lfile, final double rfrsh,
		  final Boolean ac, final boolean forcecreation,
		  final String type
  ) throws ClassNotFoundException {
	  
	  SQLMapBroker broker = null;
	  
	  logger.info("in ConnectionBrokerManager.put(" + dsrc + "," + drv + ", ...)");
	  synchronized ( semaphore ) {
		
		  
		  if ( !forcecreation ) {
			  broker =this.haveExistingBroker(dsrc, usr);
			  if (broker != null) {
				  // Found an existing broker, returning this one.
				  if (this.debug) {
					  logger.info(this.getClass() +
							  ": already have a broker for data source '"
							  + dsrc + "', user name '" + usr + "'");
				  }
				  return;
			  }
		  }
		  
		  SQLMapBroker similar = null;
		  if ( !forcecreation ) {
			  similar = this.seekSimilarBroker(dsrc, true);
		  }
		  
		  if (similar != null) {
			  if (this.debug) {
				  logger.info(this.getClass() +
						  ": have a similar broker for data source '"
						  + dsrc + "'");
			  }
			  // Clone an existing broker.
			  broker = (SQLMapBroker) similar.clone();
			  broker.username = usr;
			  broker.password = pwd;
			  
		  }
		  else {
			  // Create new broker.
			  broker = new SQLMapBroker(dsrc, drv, url, usr, pwd, minconn, maxconn, lfile, rfrsh, ac, type);
		  }
		  
		  // Create a new broker.
		  broker.createBroker();
		  
		  // Also put datasource in map with only the datasource specified.
		  this.brokerMap.put(dsrc, broker);
		  
		  if (this.debug) {
			  logger.info(this.getClass() +
					  ": putting new broker with identifier '" +
					  dsrc + "'");
		  }
		  
	  }
	  // Check transaction support.
  }

  /**
   * Gets set of all brokers identified by given url.
   * 
   * @param url
   * @return
   */
  public final Set<SQLMapBroker> getBrokersByUrl(String url) {
	  Iterator<Entry<String,SQLMapBroker>> allDatasources = brokerMap.entrySet().iterator();
	  HashSet<SQLMapBroker> all = new HashSet<SQLMapBroker>();
	  while ( allDatasources.hasNext() ) {
		  Entry<String,SQLMapBroker> entry = allDatasources.next();
//		  String datasource = (String) entry.getKey();
		  SQLMapBroker b = entry.getValue();
		  if ( b.getUrl().equals(url) ) {
			  all.add(b);
		  }
	  }
	  return all;
  }
  
  public final String getDatasourceUrl(String datasource) {
	  SQLMapBroker b = brokerMap.get(datasource);
	  if ( b != null ) {
		  return b.getUrl();
	  } else {
		  return null;
	  }
  }
  
  public synchronized int getMaxConnectionsByDatasource(String datasource) {
	  SQLMapBroker b = brokerMap.get(datasource);
	  if ( b != null && b.broker != null ) {
		  return b.broker.getMaxCount();
	  } else {
		  return -1;
	  }
  }
  
  public synchronized void setMaxConnectionsByDatasource(String datasource, int connections) {
	  // Block all new requests. 
	  // Wait until all connections are freed.
	  // Reinitialize conns array.
	  // Release block.
  }
  
  public final String getDatasourceUsername(String datasource) {
	  SQLMapBroker b = brokerMap.get(datasource);
	  if ( b != null ) {
		  return b.username;
	  } else {
		  return null;
	  }
  }
  
  public final DatabaseInfo getMetaData(final String dsrc, final String usr, final String pwd) {
    SQLMapBroker broker;
    if (usr == null) {
      if (this.debug) {
        logger.info(this.getClass() +
            ": user name is null, returning a similar broker for datasource '"
            + dsrc + "'");
      }
      broker = this.seekSimilarBroker(dsrc, false);
    }
    else {
      broker = this.haveExistingBroker(dsrc, usr);
      if (this.debug && (broker != null)) {
        logger.info(this.getClass() +
                           ": returning a broker for datasource '"
                           + dsrc + "', user name '" + usr + "', password '" + pwd + "'");
      }
    }

    if (broker != null) {
      return broker.dbInfo;
    }
    else {
      if (this.debug) {
        logger.info(this.getClass() + ": no appropriate brokers found");
      }
      return (null);
    }
  }

  public final DbConnectionBroker get(final String dsrc, final String usr, final String pwd) {
    SQLMapBroker broker;
    //logger.info("In ConnectionBrokerManager.get(" + dsrc + "," + usr + "," + pwd + ")");
    if (usr == null) {
      if (this.debug) {
        logger.info(this.getClass() +
            ": user name is null, returning a similar broker for datasource '"
            + dsrc + "'");
      }
      broker = this.seekSimilarBroker(dsrc, false);

    }
    else {
      broker = this.haveExistingBroker(dsrc, usr);
      if (this.debug && (broker != null)) {
        logger.info(this.getClass() +
                           ": returning a broker for datasource '"
                           + dsrc + "', user name '" + usr + "', password '" + pwd + "'");

      }

    }
    if (broker != null) {
      return (broker.broker);
    }
    else {
      if (this.debug) {
        logger.info(this.getClass() + ": no appropriate brokers found");
      }
      return (null);
    }

  }

  public final boolean haveSimilarBroker(final String dsrc) {
    final SQLMapBroker broker = this.seekSimilarBroker(dsrc, true);
    return (broker != null);
  }

  public final Boolean getAutoCommit(final String dsrc) {
    final SQLMapBroker broker = this.seekSimilarBroker(dsrc, true);
    if (broker != null) {
      return (broker.autocommit);
    }
    else {
      return (new Boolean(true));
    }
  }

  public final void destroy(final String dsrc) {
	  
	  synchronized ( semaphore ) {
		this.destroySimilarBroker(dsrc);
	  }
  }

  public final void setDebug(final boolean b) {
    this.debug = b;
    if (this.debug) {
      logger.info(this.getClass() +
                         "; debugging on");
    }

  }

  // ----------------------------------------------------------- private methods

  private final SQLMapBroker haveExistingBroker(final String datasource, final String usr) {

	  logger.info("In ConnectionBrokerManager.haveExistingBroker(" + datasource + "," + usr + ")");

	  SQLMapBroker broker = ( this.brokerMap.get(datasource));

	  if (! ( ( broker != null && broker.refresh == 0 ) || ( broker != null && broker.broker.isDead() ) ) ) {
		  return broker;
	  }

	  synchronized ( semaphore ) {

		  // Remember health.
		  int health = ServiceAvailability.STATUS_OK;
		  if ( broker != null ) {
			  health = broker.health;
		  }
		  brokerMap.remove(datasource);
		  if(broker!=null) {
			  try { 
				  this.put(broker.datasource, broker.driver, broker.url, broker.username, broker.password,
						  broker.minconnections, broker.maxconnections, broker.logFile,
						  broker.refresh, broker.autocommit, true, broker.type);
				  broker = ( this.brokerMap.get(datasource));
				  broker.health = health;
			  } catch (Exception e) {
				  logger.error("Error: ", e);
				  return null;
			  }
		  }
		  // Create new broker.
		  return broker;
	  }
  }
   
  /**
   * 
   * @param datasource, name of the datasource that is searched.
   * @param donotremove, if set to true do not remove a found broker, even if it is dead or refresh is 0; use this
   * flag if broker is simply used for template for creating new broker.
   * @return
   */
  private final SQLMapBroker seekSimilarBroker(final String datasource, boolean donotremove) {


	  SQLMapBroker broker = brokerMap.get( datasource );
	  if ( !( !donotremove && broker != null && ( broker.refresh == 0 || broker.broker.isDead() ) ) ) {
		  return broker;
	  } 
	  
	  synchronized ( semaphore ) {
		  int health = broker.health;
		  brokerMap.remove(datasource);
		  // Create new broker.
		  try { 
			  this.put(broker.datasource, broker.driver, broker.url, broker.username, broker.password,
					   broker.minconnections, broker.maxconnections, broker.logFile,
					   broker.refresh, broker.autocommit, true, broker.type);
			  broker = ( this.brokerMap.get(datasource));
			  broker.health = health;
		  } catch (Exception e) {
			  logger.error("Error: ", e);
			  return null;
		  }
		  return broker;    	
	  }
  }

  private final void destroySimilarBroker(final String datasource) {
   
      final SQLMapBroker broker = this.brokerMap.get(datasource);
      if (broker != null ) {
        if (broker.broker != null) {
          broker.broker.destroy();
          broker.broker = null;
        }
        this.brokerMap.remove(datasource);
        if (this.debug) {
          logger.info(this.getClass() + ": destroyed broker '" + datasource + "'");
        }
      }
  }

  public static int getInstances() {
	  return DbConnectionBroker.getInstances();
  }
  
  public int getDatasourceCount() {
	  return brokerMap.size();
  }
  
  public int getActiveConnections() {
	  Iterator<SQLMapBroker> all = brokerMap.values().iterator();
	  int total = 0;
	  while ( all.hasNext() ) {
		  SQLMapBroker b = all.next();
		  if ( b.broker != null ) {
			  total += b.broker.getUseCount();
		  }
	  }
	  return total;
  }
  
  public int getActiveConnectionsByUrl(String url) {
	  Set<SQLMapBroker> brokers = getBrokersByUrl(url);
	  int total = 0;
	  if ( brokers.size() > 0 ) {
		  Iterator<SQLMapBroker> i = brokers.iterator();
			 while ( i.hasNext() ) {
				 SQLMapBroker b = i.next();
				 total++;
			 }
	  } 
	  return total;
  }

  public int getActiveConnectionsByDatasource(String datasource) {
	  SQLMapBroker broker = brokerMap.get(datasource);
	  if ( broker != null && broker.broker != null ) {
		  return broker.broker.getUseCount();
	  }
	  return -1;
  }
  
  public String getDefinedDatasources() {
	  Iterator<SQLMapBroker> all = brokerMap.values().iterator();
	  StringBuffer sb = new StringBuffer();
	  while ( all.hasNext() ) {
		  sb.append(all.next().datasource + ",");
	  }
	  if ( sb.length() > 0 ) {
		  return sb.substring(0, sb.length() - 1);
	  } else {
		  return "";
	  }
  }
  
  public int getHealthByUrl(String url) {
	  
	  int maxHealth = 0;
	  Set<SQLMapBroker> brokers = getBrokersByUrl(url);
	  if ( brokers.size() > 0 ) {
		 Iterator<SQLMapBroker> i = brokers.iterator();
		 while ( i.hasNext() ) {
			 SQLMapBroker b = i.next();
			 if ( b.health > maxHealth ) {
				 maxHealth = b.health;
			 }
		 }
	  } else {
		  logger.warn("Could not find datasource associated with url: " + url);
		  return 0;
	  }
	  return maxHealth;
  }
	
  public int getHealth(String datasource) {
	  SQLMapBroker broker = ( this.brokerMap.get(datasource.replaceAll("'", "")));
	  if ( broker == null ) {
		  try {
			  new SQLMap().setReload(datasource);
		  } catch (Throwable e) {
			  return ServiceAvailability.STATUS_UNKNOWN; // Be careful.
		  } 
		  broker = ( this.brokerMap.get(datasource.replaceAll("'", "")));
		  if ( broker == null ) {
			  return ServiceAvailability.STATUS_UNKNOWN;
		  }
	  }
	  return broker.health;
	}
  
  public void setHealth(String datasource, int h) {
	  SQLMapBroker broker = ( this.brokerMap.get(datasource.replaceAll("'", "")));
	  if ( broker == null ) {
		  logger.warn("Could not set health of resource: " + datasource);
	  } else {
		  broker.health = h;
	  }
  }
  
  public void setHealthByUrl(String url, int health) {
	  Set<SQLMapBroker> brokers = getBrokersByUrl(url);
	  if ( brokers.size() > 0 ) {
		 Iterator<SQLMapBroker> i = brokers.iterator();
		 while ( i.hasNext() ) {
			 SQLMapBroker b = i.next();
			 b.health = health;
		 }
	  } else {
		  logger.warn("Could not find datasource associated with url: " + url);
	  }
  }
  
  /**
   * This function checks whether the resource is available (true) or temporarily unavailable.
   * 
   */
  public boolean isAvailable(String datasource) {

	  // Make sure to strip "'". 
	  SQLMapBroker broker = ( this.brokerMap.get(datasource.replaceAll("'", "")));
	  if ( broker == null ) {
		  return true; // Try it to prevent deadlocking on changed web service that can never be reached due to former unavailability.
	  }
	  
	  int useCount = broker.broker.getUseCount();
	  int totalCount = broker.broker.getMaxCount();

	  boolean available = ( totalCount > useCount );
	  if ( available ) { // Reset currentWaitingTime if resource is available.
		  currentWaitingTime = 0;
	  }
	  
	  broker.available = available;
	  
	  return available;
  }

  /**
   * Return waiting time for unavailable resource in millis.
   * 
   */

  private int currentWaitingTime = 0;
  
  public int getWaitingTime(String resourceId) {
	  currentWaitingTime += 500; // Offset current waiting time...
	  return currentWaitingTime;
  }
  
  private class SQLMapBroker
      extends Object
      implements Cloneable {
    public String datasource;
    public String driver;
    public String url;
    public String type;
    
	public String username;
    public String password;
    public int minconnections;
    public int maxconnections;
    public String logFile;
    public double refresh;
    public Boolean autocommit;
    public DbConnectionBroker broker;
    public DatabaseInfo dbInfo = null;
    
    public boolean available = true;
    public int health;

    public SQLMapBroker(final String dsrc, final String drv, final String url,
                        final String usr,
                        final String pwd, final int minconn,
                        final int maxconn,
                        final String lfile, final double rfrsh,
                        final Boolean ac, final String type) {
      this.datasource = dsrc;
      this.driver = drv;
      this.url = url;
      this.username = usr;
      this.password = pwd;
      this.minconnections = minconn;
      this.maxconnections = maxconn;
      this.logFile = lfile;
      this.refresh = rfrsh;
      this.autocommit = ac;
      this.type = type;

    }

    private void createBroker() throws ClassNotFoundException {
    	this.broker = new DbConnectionBroker(this.driver, this.url, this.username,
    			this.password,
    			this.minconnections,
    			this.maxconnections, this.logFile,
    			this.refresh);
    	this.broker.setDbIdentifier(this.type);
    	// Only get metadata if started in 'broker' mode (i.e. refresh > 0 )
    	if (this.broker != null && refresh > 0) {
    		Connection c = this.broker.getConnection();
    		if (c != null) {
    			try {
    				DatabaseMetaData dbmd = c.getMetaData();
    				broker.supportsAutocommit = dbmd.supportsTransactions();
    				dbInfo = new DatabaseInfo(dbmd, this.datasource);
    			}
    			catch (SQLException ex) {
    				logger.error("Error: ", ex);
    			}
    			finally {
    				this.broker.freeConnection(c);
    			}
    		}
    	}
    	
    }

    public Object clone() {
      final SQLMapBroker y = new SQLMapBroker(
          this.datasource, this.driver, this.url, this.username, this.password,
          this.minconnections,
          this.maxconnections, this.logFile, this.refresh, this.autocommit, this.type);
      return (y);
    }

    public String getUrl() {
		return url;
	}

  }

} // public class ConnectionBrokerManager
// EOF: $RCSfile$ //
