package com.dexels.navajo.adapter;

import java.util.HashMap;
import org.dexels.grus.DbConnectionBroker;
import java.util.Set;
import java.util.Iterator;

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

public class ConnectionBrokerManager
    extends HashMap {

  public final String SRCUSERDELIMITER = ":";

  private boolean debug = false;

  public ConnectionBrokerManager() {
    super();
  }

  public ConnectionBrokerManager(final boolean b) {
    this.debug = b;
  }

  // ------------------------------------------------------------ public methods

  public void put(final String dsrc,
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

    }
    else {
      broker = new SQLMapBroker(dsrc, drv, url, usr, pwd, minconn, maxconn,
                                lfile, rfrsh, ac);

    }
    broker.createBroker();
    final String key = dsrc + this.SRCUSERDELIMITER + usr;
    this.put(key, broker);
    if (this.debug) {
      System.out.println(this.getClass() +
                         ": putting new broker with identifier '" +
                         key + "'");
    }

  }

  public DbConnectionBroker get(final String dsrc, final String usr) {
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
                           + dsrc + "', user name '" + usr + "'");

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

  public boolean haveSimilarBroker(final String dsrc) {
    final SQLMapBroker broker = this.seekSimilarBroker(dsrc);
    return (broker != null);
  }

  public Boolean getAutoCommit(final String dsrc) {
    final SQLMapBroker broker = this.seekSimilarBroker(dsrc);
    if (broker != null) {
      return (broker.autocommit);
    }
    else {
      return (new Boolean(true));
    }
  }

  public void destory(final String dsrc, final String usr) {
    SQLMapBroker broker = this.haveExistingBroker(dsrc, usr);
    if (broker != null) {
      broker.broker.destroy();
      broker = null;
      this.remove(dsrc + this.SRCUSERDELIMITER + usr);
    }
  }

  // ----------------------------------------------------------- private methods

  private SQLMapBroker haveExistingBroker(final String datasource,
                                          final String usr) {
    final String target = datasource + this.SRCUSERDELIMITER + usr;
    final Set keys = this.keySet();
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String key = (String) iter.next();
      if (key.equals(target)) {
        return ( (SQLMapBroker)this.get(key));
      }
    }

    return (null);
  }

  private SQLMapBroker seekSimilarBroker(final String datasource) {
    final Set keys = this.keySet();
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String key = (String) iter.next();
      final SQLMapBroker broker = (SQLMapBroker)this.get(key);
      if (broker.datasource.equals(datasource)) {
        return (broker);
      }
    }

    return (null);
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