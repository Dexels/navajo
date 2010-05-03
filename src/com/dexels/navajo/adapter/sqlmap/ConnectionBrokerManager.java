package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.adapter.sqlmap.DatabaseInfo;


/**
 * <p>Title: Connection Broker Manager
 * <p>Description: helps out the SQLMap with managing brokers, specifically it keeps
 * independent brokers based on both the data source name and the user
 * which can be set on the fly in a NavaScript</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthew Eichler <meichler@dexels.com>
 * @version $Id$
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.resource.ResourceManager;

import org.dexels.grus.DbConnectionBroker;
import java.sql.*;

public class ConnectionBrokerManager extends Object implements ResourceManager {

  private Map brokerMap = Collections.synchronizedMap(new HashMap());
  private boolean debug = true;

  private static Object semaphore = new Object();
  
  public ConnectionBrokerManager() {
    super();
  }

  public ConnectionBrokerManager(final boolean b) {
    super();
    this.debug = b;
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
		  final Boolean ac, final boolean forcecreation
  ) throws ClassNotFoundException {
	  
	  SQLMapBroker broker = null;
	  
	  //System.err.println("in ConnectionBrokerManager.put(" + dsrc + "," + drv + ", ...)");
	  synchronized ( semaphore ) {
		
		  
		  if ( !forcecreation ) {
			  broker =this.haveExistingBroker(dsrc, usr);
			  if (broker != null) {
				  // Found an existing broker, returning this one.
				  if (this.debug) {
					  System.out.println(this.getClass() +
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
				  System.out.println(this.getClass() +
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
			  broker = new SQLMapBroker(dsrc, drv, url, usr, pwd, minconn, maxconn, lfile, rfrsh, ac);
		  }
		  
		  // Create a new broker.
		  broker.createBroker();
		  
		  // Also put datasource in map with only the datasource specified.
		  this.brokerMap.put(dsrc, broker);
		  
		  if (this.debug) {
			  System.out.println(this.getClass() +
					  ": putting new broker with identifier '" +
					  dsrc + "'");
		  }
		  
	  }
	  // Check transaction support.
	  if ( broker != null ) {
		  
	  }
  }

  public final String getDatasourceUrl(String datasource) {
	  SQLMapBroker b = (SQLMapBroker) brokerMap.get(datasource);
	  if ( b != null ) {
		  return b.getUrl();
	  } else {
		  return null;
	  }
  }
  
  public final DatabaseInfo getMetaData(final String dsrc, final String usr, final String pwd) {
    SQLMapBroker broker;
    if (usr == null) {
      if (this.debug) {
        System.out.println(this.getClass() +
            ": user name is null, returning a similar broker for datasource '"
            + dsrc + "'");
      }
      broker = this.seekSimilarBroker(dsrc, false);
    }
    else {
      broker = this.haveExistingBroker(dsrc, usr);
      if (this.debug && (broker != null)) {
        System.out.println(this.getClass() +
                           ": returning a broker for datasource '"
                           + dsrc + "', user name '" + usr + "', password '" + pwd + "'");
      }
    }

    if (broker != null) {
      return broker.dbInfo;
    }
    else {
      if (this.debug) {
        System.out.println(this.getClass() + ": no appropriate brokers found");
      }
      return (null);
    }
  }

  public final DbConnectionBroker get(final String dsrc, final String usr, final String pwd) {
    SQLMapBroker broker;
    //System.err.println("In ConnectionBrokerManager.get(" + dsrc + "," + usr + "," + pwd + ")");
    if (usr == null) {
      if (this.debug) {
        System.out.println(this.getClass() +
            ": user name is null, returning a similar broker for datasource '"
            + dsrc + "'");
      }
      broker = this.seekSimilarBroker(dsrc, false);

    }
    else {
      broker = this.haveExistingBroker(dsrc, usr);
      if (this.debug && (broker != null)) {
        System.out.println(this.getClass() +
                           ": returning a broker for datasource '"
                           + dsrc + "', user name '" + usr + "', password '" + pwd + "'");

      }

    }
    if (broker != null) {
      return (broker.broker);
    }
    else {
      if (this.debug) {
        System.out.println(this.getClass() + ": no appropriate brokers found");
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
      System.out.println(this.getClass() +
                         "; debugging on");
    }

  }

  // ----------------------------------------------------------- private methods

  private final SQLMapBroker haveExistingBroker(final String datasource, final String usr) {

	  System.err.println("In ConnectionBrokerManager.haveExistingBroker(" + datasource + "," + usr + ")");

	  SQLMapBroker broker = ( (SQLMapBroker)this.brokerMap.get(datasource));

	  if (! ( ( broker != null && broker.refresh == 0 ) || ( broker != null && broker.broker.isDead() ) ) ) {
		  return broker;
	  }

	  synchronized ( semaphore ) {

		  //System.err.println("Detected dead broker, removing it and creating new one");
		  brokerMap.remove(datasource);
		  // Create new broker.
		  try { 
			  this.put(broker.datasource, broker.driver, broker.url, broker.username, broker.password,
					  broker.minconnections, broker.maxconnections, broker.logFile,
					  broker.refresh, broker.autocommit, true);
			  broker = ( (SQLMapBroker)this.brokerMap.get(datasource));
		  } catch (Exception e) {
			  e.printStackTrace(System.err);
			  return null;
		  }
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


	  SQLMapBroker broker = (SQLMapBroker) brokerMap.get( datasource );
	  if ( !( !donotremove && ( broker.refresh == 0 || ( broker != null && broker.broker.isDead()) ) ) ) {
		  return broker;
	  } 
	  
	  synchronized ( semaphore ) {
		  if ( broker != null ) {
			  brokerMap.remove(datasource);
			  // Create new broker.
			  try { 
				  this.put(broker.datasource, broker.driver, broker.url, broker.username, broker.password,
						   broker.minconnections, broker.maxconnections, broker.logFile,
						   broker.refresh, broker.autocommit, true);
				  broker = ( (SQLMapBroker)this.brokerMap.get(datasource));
			  } catch (Exception e) {
				  e.printStackTrace(System.err);
				  return null;
			  }
		  } 
		  return broker;    	
	  }
  }

  private final void destroySimilarBroker(final String datasource) {
   
      final SQLMapBroker broker = (SQLMapBroker)this.brokerMap.get(datasource);
      if (broker != null ) {
        if (broker.broker != null) {
          broker.broker.destroy();
          broker.broker = null;
        }
        this.brokerMap.remove(datasource);
        if (this.debug) {
          System.out.println(this.getClass() + ": destroyed broker '" + datasource + "'");
        }
      }
  }

  public static int getInstances() {
	  return DbConnectionBroker.getInstances();
  }
  
  private class SQLMapBroker
      extends Object
      implements Cloneable {
    public String datasource;
    public String driver;
    public String url;
    
	public String username;
    public String password;
    public int minconnections;
    public int maxconnections;
    public String logFile;
    public double refresh;
    public Boolean autocommit;
    public DbConnectionBroker broker;
    public DatabaseInfo dbInfo = null;

    public SQLMapBroker(final String dsrc, final String drv, final String url,
                        final String usr,
                        final String pwd, final int minconn,
                        final int maxconn,
                        final String lfile, final double rfrsh,
                        final Boolean ac) {
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

    }

    public void createBroker() throws ClassNotFoundException {
    	this.broker = new DbConnectionBroker(this.driver, this.url, this.username,
    			this.password,
    			this.minconnections,
    			this.maxconnections, this.logFile,
    			this.refresh);
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
    				ex.printStackTrace(System.err);
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
          this.maxconnections, this.logFile, this.refresh, this.autocommit);
      return (y);
    }

    public String getUrl() {
		return url;
	}

  }

  public boolean isAvailable(String datasource) {
	  
	  // Make sure to strip "'".
	  SQLMapBroker broker = ( (SQLMapBroker)this.brokerMap.get(datasource.replaceAll("'", "")));
	  if ( broker == null ) {
		  System.err.println("Could not determine availability of resource: " + datasource);
		  return true;
	  }
	  int useCount = broker.broker.getUseCount();
	  int totalCount = broker.broker.getMaxCount();
	  
	  return ( totalCount > useCount );
	  
  }

/**
 * Return waiting time for unavailable resource in millis.
 * 
 */
public int getWaitingTime(String resourceId) {
	return 500;
}

} // public class ConnectionBrokerManager
// EOF: $RCSfile$ //
