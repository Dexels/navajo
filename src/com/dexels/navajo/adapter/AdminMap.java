package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import org.dexels.grus.DbConnectionBroker;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import com.dexels.navajo.mapping.AsyncMappable;
import java.util.Date;

public class AdminMap implements Mappable {

  public int openConnections;
  public int requestCount;
  public float requestRate;
  public Date startTime;
  public String scriptPath;
  public AccessMap [] users;
  public AsyncProxy [] asyncThreads;
  public String accessId;
  public String vendor;
  public String productName;
  public String version;
  public String repository;
  public String configPath;
  public String adapterPath;
  public String compiledScriptPath;
  public String rootPath;
  public boolean supportsHotCompile;
  public boolean supportsAsync;
  public boolean supportsStore;
  public boolean monitorOn;
  public String storeLocation;
  public int classLoaderInstances;
  public String serverId;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    NavajoConfig nc = Dispatcher.getNavajoConfig();
    scriptPath = nc.getScriptPath();
    configPath = nc.getConfigPath();
    adapterPath = nc.getAdapterPath();
    compiledScriptPath = nc.getCompiledScriptPath();
    rootPath = nc.getRootPath();
    supportsHotCompile = nc.isHotCompileEnabled();
    supportsAsync = nc.isAsyncEnabled();
    supportsStore = ( nc.getAsyncStore() != null );
    storeLocation = nc.dbPath;
    serverId = Dispatcher.serverId;
  }

  /**
    * Some admin functions.
    *
    * @param datasourceName
    * @throws MappableException
    * @throws UserException
    */
   public final int getOpenConnections(String datasource) throws UserException {
     SQLMap sql = new SQLMap();
     sql.setDatasource(datasource);

     if (sql.fixedBroker == null || sql.fixedBroker.get(sql.datasource, sql.username, sql.password) == null) {
       System.err.println("Could not create connection to datasource " +
                          sql.datasource + ", using username " +
                          sql.username);
       return 0;
     }
     return sql.fixedBroker.get(sql.datasource, sql.username, sql.password).getUseCount();
   }

   /**
    * Some admin functions.
    *
    * @param datasourceName
    * @throws MappableException
    * @throws UserException
    */
   public final int getMaxConnections(String datasource) throws UserException {
     SQLMap sql = new SQLMap();
     sql.setDatasource(datasource);

     if (sql.fixedBroker == null || sql.fixedBroker.get(sql.datasource, sql.username, sql.password) == null) {
       System.err.println("Could not create connection to datasource " +
                          sql.datasource + ", using username " +
                          sql.username);
       return 0;
     }
     return sql.fixedBroker.get(sql.datasource, sql.username, sql.password).getSize();
   }

   public int getRequestCount() {
     return (int) com.dexels.navajo.server.Dispatcher.requestCount;
   }

   public AsyncProxy [] getAsyncThreads() {

     System.err.println("IN GETASYNCTHREADS()......");
     HashMap all = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore;
     Iterator iter = all.values().iterator();
     ArrayList l = new ArrayList();
     while (iter.hasNext()) {
       AsyncMappable am = (AsyncMappable) iter.next();
       Access ac = (Access) com.dexels.navajo.mapping.AsyncStore.getInstance().accessStore.get(am.pointer);
       AsyncProxy o = new AsyncProxy();
       o.user = ac.rpcUser;
       o.rpcName = ac.rpcName;
       o.setPointer(am.getPointer());
       o.startDate = am.getStartDate();
       o.name = am.getName();
       o.startTime = am.getStartTime();
       o.running = am.getRunning();
       o.interrupt = am.getInterrupt();
       o.accessId = ac.accessID;
       o.totaltime = (int) ( System.currentTimeMillis() - am.getStartDate().getTime() );
       o.ipAddress = ac.ipAddress;
       o.host = ac.hostName;
       o.kill = am.kill;
       try {
         o.percReady = am.getPercReady();
       } catch (Exception e) { e.printStackTrace(System.err); }
       l.add(o);
     }
     AsyncProxy [] objects = new AsyncProxy[l.size()];
     return (AsyncProxy []) l.toArray(objects);
   }

   public AccessMap [] getUsers() {
      HashSet all = com.dexels.navajo.server.Dispatcher.accessSet;
      Iterator iter = all.iterator();
      ArrayList d = new ArrayList();
      while (iter.hasNext()) {
        Access a = (Access) iter.next();
        AccessMap am = new AccessMap();
        try {
          am.load(null, null, a, null);
          d.add(am);
        }
        catch (Exception ex) {
          ex.printStackTrace(System.err);
        }
      }
      AccessMap [] ams = new AccessMap[d.size()];

      return (AccessMap []) d.toArray(ams);
   }

   public void store() throws MappableException, UserException {
   }

   public void kill() {

  }
  public void setAsyncThreads(AsyncProxy[] asyncThreads) {
    this.asyncThreads = asyncThreads;
  }
  public Date getStartTime() {
    return com.dexels.navajo.server.Dispatcher.startTime;
  }
  public float getRequestRate() {
    float timespan =  ( new java.util.Date().getTime() - com.dexels.navajo.server.Dispatcher.startTime.getTime() ) / (float) 1000.0;
    return ((float) getRequestCount() / timespan );
  }

  public String getProductName() {
    return com.dexels.navajo.server.Dispatcher.product;
  }
  public String getVendor() {
    return com.dexels.navajo.server.Dispatcher.vendor;
  }
  public String getVersion() {
    return com.dexels.navajo.server.Dispatcher.version;
  }
  public String getRepository() {
    if (com.dexels.navajo.server.Dispatcher.getRepository() != null) {
      return com.dexels.navajo.server.Dispatcher.getRepository().getClass().
          getName();
    } else {
      return "No repository configured";
    }
  }
  public String getConfigPath() {
    return configPath;
  }
  public String getScriptPath() {
    return scriptPath;
  }
  public String getAdapterPath() {
    return adapterPath;
  }
  public String getCompiledScriptPath() {
    return compiledScriptPath;
  }
  public String getRootPath() {
    return rootPath;
  }
  public boolean getSupportsAsync() {
    return supportsAsync;
  }
  public boolean getSupportsHotCompile() {
    return supportsHotCompile;
  }
  public boolean getSupportsStore() {
    return supportsStore;
  }
  public String getStoreLocation() {
    return storeLocation;
  }
  public int getClassLoaderInstances() {
    return classLoaderInstances;
  }
  public String getServerId() {
    return serverId;
  }

  public boolean getMonitorOn() {
    return Dispatcher.getNavajoConfig().isMonitorOn();
  }

  /**
   * Sets the full log monitor to on for all requests.
   *
   * @param monitorOn
   */
  public void setMonitorOn(boolean monitorOn) {
    System.err.println("Setting monitor to: " + monitorOn);
    this.monitorOn = monitorOn;
    Dispatcher.getNavajoConfig().setMonitorOn(monitorOn);
  }
}