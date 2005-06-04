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

import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import com.dexels.navajo.server.UserException;

import org.dexels.grus.DbConnectionBroker;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.*;

public class ConnectionBrokerManager extends Object {

  public final String SRCUSERDELIMITER = ":";

  private Map brokerMap = new HashMap();
  private boolean debug = false;

  public ConnectionBrokerManager() {
    super();
  }

  public ConnectionBrokerManager(final boolean b) {
    super();
    this.debug = b;
  }

  // ------------------------------------------------------------ public methods

  public final void put(final String dsrc,
                  final String drv, final String url,
                  final String usr,
                  final String pwd, final int minconn,
                  final int maxconn,
                  final String lfile, final double rfrsh,
                  final Boolean ac
                  ) throws ClassNotFoundException {
    SQLMapBroker broker = this.haveExistingBroker(dsrc, usr);
    if (broker != null) {
      if (this.debug) {
        System.out.println(this.getClass() +
                           ": already have a broker for data source '"
                           + dsrc + "', user name '" + usr + "'");
      }
      return;
    }
    SQLMapBroker similar = this.seekSimilarBroker(dsrc);
    if (similar != null) {
      if (this.debug) {
        System.out.println(this.getClass() +
                           ": have a similar broker for data source '"
                           + dsrc + "'");
      }
      broker = (SQLMapBroker) similar.clone();
      broker.username = usr;
      broker.password = pwd;

    }
    else {
      broker = new SQLMapBroker(dsrc, drv, url, usr, pwd, minconn, maxconn, lfile, rfrsh, ac);
    }
    broker.createBroker();
    final String key = dsrc + this.SRCUSERDELIMITER + usr;
    this.brokerMap.put(key, broker);
    if (this.debug) {
      System.out.println(this.getClass() +
                         ": putting new broker with identifier '" +
                         key + "'");
    }

  }

  public final void put(final String datasource, final String username, final String password) throws
      UserException,
      ClassNotFoundException {
    SQLMapBroker broker = this.haveExistingBroker(datasource, username);
    if (broker != null) {
      if (this.debug) {
        System.out.println(this.getClass() +
                           ": already have a broker for data source '"
                           + datasource + "', user name '" + username + "'");
      }
      return;
    }

    broker = this.seekSimilarBroker(datasource);
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

  public final DatabaseInfo getMetaData(final String dsrc, final String usr, final String pwd) {
    SQLMapBroker broker;
    if (usr == null) {
      if (this.debug) {
        System.out.println(this.getClass() +
            ": user name is null, returning a similar broker for datasource '"
            + dsrc + "'");
      }
      broker = this.seekSimilarBroker(dsrc);
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
      broker = this.seekSimilarBroker(dsrc);

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
    final SQLMapBroker broker = this.seekSimilarBroker(dsrc);
    return (broker != null);
  }

  public final Boolean getAutoCommit(final String dsrc) {
    final SQLMapBroker broker = this.seekSimilarBroker(dsrc);
    if (broker != null) {
      return (broker.autocommit);
    }
    else {
      return (new Boolean(true));
    }
  }

  public final void destroy(final String dsrc, final String usr) {
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

  public final void setDebug(final boolean b) {
    this.debug = b;
    if (this.debug) {
      System.out.println(this.getClass() +
                         "; debugging on");
    }

  }

  // ----------------------------------------------------------- private methods

  private final SQLMapBroker haveExistingBroker(final String datasource,
                                          final String usr) {
    final String target = datasource + this.SRCUSERDELIMITER + usr;
    final Set keys = this.brokerMap.keySet();
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String key = (String) iter.next();
      if (key.equals(target)) {
        return ( (SQLMapBroker)this.brokerMap.get(key));
      }
    }

    return (null);
  }

  private final SQLMapBroker seekSimilarBroker(final String datasource) {
    final Set keys = this.brokerMap.keySet();
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String key = (String) iter.next();
      final SQLMapBroker broker = (SQLMapBroker)this.brokerMap.get(key);
      if (broker.datasource.equals(datasource)) {
        return (broker);
      }
    }

    return (null);
  }

  private final void destroySimilarBroker(final String datasource) {
    final Set keys = this.brokerMap.keySet();
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
      if (this.broker != null) {
      Connection c = this.broker.getConnection();
      if (c != null) {
        try {
          System.err.print("GETTING METADATA FOR " + url + "...");
          DatabaseMetaData dbmd = c.getMetaData();
          dbInfo = new DatabaseInfo(dbmd, this.datasource);
          System.err.println("...GOT IT!");
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
