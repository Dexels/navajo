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

import org.dexels.grus.DbConnectionBroker;
import java.sql.*;

public class ConnectionBrokerManager extends Object {

  public final String SRCUSERDELIMITER = ":";

  private Map brokerMap = Collections.synchronizedMap(new HashMap());
  private boolean debug = false;

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
	  synchronized ( semaphore ) {
		  SQLMapBroker broker = null;
		  
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
		  final String key = dsrc + this.SRCUSERDELIMITER + usr;
		  
		  this.brokerMap.put(key, broker);
		  if (this.debug) {
			  System.out.println(this.getClass() +
					  ": putting new broker with identifier '" +
					  key + "'");
		  }
		  
	  }
  }

  public final void put(final String datasource, final String username, final String password) throws
  UserException,
  ClassNotFoundException {
	  synchronized ( semaphore ) {
		  SQLMapBroker broker = this.haveExistingBroker(datasource, username);
		  if (broker != null) {
			  if (this.debug) {
				  System.out.println(this.getClass() +
						  ": already have a broker for data source '"
						  + datasource + "', user name '" + username + "'");
			  }
			  return;
		  }
		  
		  broker = this.seekSimilarBroker(datasource, true);
		  if (broker == null) {
			  throw new UserException( -1, "data source for '" + datasource +
			  "' not configured");
		  }
		  
		  final SQLMapBroker newbroker = (SQLMapBroker) broker.clone();
		  newbroker.username = username;
		  newbroker.password = password;
		  newbroker.createBroker();
		  final String key = datasource + this.SRCUSERDELIMITER + username;
		  this.brokerMap.put(key, newbroker);
		  if (this.debug) {
			  System.out.println(this.getClass() + ": created a new broker '" + key +
			  "' using a clone");
		  }
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

  public final void destroy(final String dsrc, final String usr) {
	  
	  synchronized ( semaphore ) {
		  
		  if (usr != null || usr.length() > 0) {
			  final SQLMapBroker broker = this.haveExistingBroker(dsrc, usr);
			  if (broker.broker != null) {
				  broker.broker.destroy();
				  broker.broker = null;
			  }
			  final String key = dsrc + this.SRCUSERDELIMITER + usr;
			  this.brokerMap.remove(key);
			  if (this.debug) {
				  System.out.println(this.getClass() + ": destroyed broker '" + key + "'");
			  }
		  }
		  else {
			  this.destroySimilarBroker(dsrc);
		  }
		  
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
	  
	  synchronized ( semaphore ) {
		  final String target = datasource + this.SRCUSERDELIMITER + usr;
		  
		  SQLMapBroker broker = ( (SQLMapBroker)this.brokerMap.get(target));
		  
		  if ( ( broker != null && broker.refresh == 0 ) || ( broker != null && broker.broker.isDead() ) ) {
			  //System.err.println("Detected dead broker, removing it and creating new one");
			  brokerMap.remove(target);
			  // Create new broker.
			  try { 
				  this.put(broker.datasource, broker.driver, broker.url, broker.username, broker.password,
						  broker.minconnections, broker.maxconnections, broker.logFile,
						  broker.refresh, broker.autocommit, true);
				  broker = ( (SQLMapBroker)this.brokerMap.get(target));
			  } catch (Exception e) {
				  e.printStackTrace(System.err);
				  return null;
			  }
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
	  
	  synchronized ( semaphore ) {
		  
		  final Set keys = new HashSet(this.brokerMap.keySet());
		  final Iterator iter = keys.iterator();
		  while (iter.hasNext()) {
			  final String key = (String) iter.next();
			  SQLMapBroker broker = (SQLMapBroker)this.brokerMap.get(key);
			  if (broker.datasource.equals(datasource)) {
				  //return (broker);
				  if ( !donotremove && ( broker.refresh == 0 || ( broker != null && broker.broker.isDead()) ) ) {
					  //System.err.println("Detected dead broker, removing it and creating new one");
					  brokerMap.remove(key);
					  // Create new broker.
					  try { 
						  this.put(broker.datasource, broker.driver, broker.url, broker.username, broker.password,
								  broker.minconnections, broker.maxconnections, broker.logFile,
								  broker.refresh, broker.autocommit, true);
						  broker = ( (SQLMapBroker)this.brokerMap.get(key));
					  } catch (Exception e) {
						  e.printStackTrace(System.err);
						  return null;
					  }
				  } 
				  return broker;    	
			  }
		  }
		  return (null);
	  }
  }

  private final void destroySimilarBroker(final String datasource) {
    final Set keys = new HashSet(this.brokerMap.keySet());
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String key = (String) iter.next();
      final SQLMapBroker broker = (SQLMapBroker)this.brokerMap.get(key);
      if (broker.datasource.equals(datasource)) {
        if (broker.broker != null) {
          broker.broker.destroy();
          broker.broker = null;
        }
        final String bkey = datasource + this.SRCUSERDELIMITER +
            broker.username;
        this.brokerMap.remove(bkey);
        if (this.debug) {
          System.out.println(this.getClass() + ": destroyed broker '" + bkey +
                             "'");
        }
        return;
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
    				//System.err.print("GETTING METADATA FOR " + url + "...");
    				DatabaseMetaData dbmd = c.getMetaData();
    				dbInfo = new DatabaseInfo(dbmd, this.datasource);
    				//System.err.println("...GOT IT!");
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

  }

} // public class ConnectionBrokerManager
// EOF: $RCSfile$ //
