package com.dexels.navajo.adapter;

import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;


import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class AdminMap implements Mappable {

  public int openConnections;
  public int requestCount;
  public float requestRate;
  public Date startTime;
  public String scriptPath;
  public AccessMap [] users;
  public AsyncProxy [] asyncThreads;
  public ClassCount [] classCounts;
  public String accessId;
  public String vendor;
  public String productName;
  public String version;
  public String repository;
  public String configPath;
  public String adapterPath;
  public String compiledScriptPath;
  public String rootPath;
  public String webservice;
  public String documentClass;
 // public WebserviceAccess webserviceAccess;
  public boolean supportsHotCompile;
  public boolean supportsAsync;
  public boolean supportsStore;
  public boolean supportsIntegrity;
  public boolean supportsStatistics;
  public boolean supportsLocks;
  public boolean aliveConnection;
  public boolean collectGarbage;
  public boolean resetAdapters;

  /**
   * Monitor parameters.
   */
  public boolean monitorOn;
  public String monitorUsers;
  public String monitorWS;
  public int monitorTotaltime;

  public String storeLocation;
  public int classLoaderInstances;
  public int scriptClassInstances;
  public String serverId;

  // RequestRate windowSize
  public int requestRateWindowSize;

  private NavajoConfig myConfig = null;
  private Access myAccess = null;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    NavajoConfig nc = Dispatcher.getInstance().getNavajoConfig();
    scriptPath = nc.getScriptPath();
    configPath = nc.getConfigPath();
    adapterPath = nc.getAdapterPath();
    compiledScriptPath = nc.getCompiledScriptPath();
    rootPath = nc.getRootPath();
    
    //supportsHotCompile = nc.isHotCompileEnabled();
    supportsAsync = nc.isAsyncEnabled();
    supportsStore = ( nc.getAsyncStore() != null );
    supportsIntegrity = nc.isIntegrityWorkerEnabled();
    supportsStatistics = nc.isStatisticsRunnerEnabled();
    supportsLocks = nc.isLockManagerEnabled();
    
    storeLocation = nc.getDbPath();
    serverId = Dispatcher.getInstance().getApplicationId();
    
    myConfig = config;
    myAccess = access;
  }

  
  /**
    * Some admin functions.
    *
    * @param datasourceName
    * @throws MappableException
    * @throws UserException
    */
  public final int getOpenConnections(String datasource) {
	  try {
		  SQLMap sql = new SQLMap();
		  sql.setDatasource(datasource);

		  if (SQLMap.fixedBroker == null || SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password) == null) {
			  System.err.println("Could not create connection to datasource " +
					  sql.datasource + ", using username " +
					  sql.username);
			  return 0;
		  }
		  int c = SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password).getUseCount();

		  sql.store();

		  return c;
	  } catch (Throwable e) { e.printStackTrace(System.err);  }
	  return 0;
  }

   public final boolean getAliveConnection(String datasource) {
		boolean b = true;
		SQLMap sql = new SQLMap();
		try {

			sql.load(null, null, myAccess, myConfig);
			sql.setDatasource(datasource);
			sql.createConnection();
			if (sql.con == null) {
				b = false;
			}

		} catch (Throwable e1) {
			b = false;
		} finally {
			try {
				sql.store();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return b;
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

     if (SQLMap.fixedBroker == null || SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password) == null) {
       System.err.println("Could not create connection to datasource " +
                          sql.datasource + ", using username " +
                          sql.username);
       return 0;
     }
     return SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password).getSize();
   }

   public int getRequestCount() {
     return (int) com.dexels.navajo.server.Dispatcher.getInstance().requestCount;
   }

   public AsyncProxy [] getAsyncThreads() {

     //System.err.println("IN GETASYNCTHREADS()......");
     Map all = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore;
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
       o.stackTrace = am.getStackTrace();
       o.lockClass = am.getLockClass();
       o.lockName = am.getLockName();
       o.lockOwner = am.getLockOwner();
       o.waiting = am.isWaiting();
       try {
         o.percReady = am.getPercReady();
       } catch (Exception e) { e.printStackTrace(System.err); }
       l.add(o);
     }
     AsyncProxy [] objects = new AsyncProxy[l.size()];
     return (AsyncProxy []) l.toArray(objects);
   }

   public AccessMap [] getUsers() {
      Set all = new HashSet(com.dexels.navajo.server.Dispatcher.getInstance().accessSet);
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
    return Dispatcher.getInstance().getRequestRate();

//    float timespan =  ( new java.util.Date().getTime() - com.dexels.navajo.server.Dispatcher.startTime.getTime() ) / (float) 1000.0;
//    return ((float) getRequestCount() / timespan );
  }

  public String getProductName() {
    return com.dexels.navajo.server.Dispatcher.product;
  }
  public String getVendor() {
    return com.dexels.navajo.server.Dispatcher.vendor;
  }
  public String getVersion() {
	return Dispatcher.getVersion() + " (" + Dispatcher.getEdition() + ")";
  }
  public String getRepository() {
    if (com.dexels.navajo.server.Dispatcher.getInstance().getRepository() != null) {
      return com.dexels.navajo.server.Dispatcher.getInstance().getRepository().getClass().
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
  public boolean getSupportsIntegrity() {
	  return supportsIntegrity;
  }
  public boolean getSupportsStatistics() {
	  return supportsStatistics;
  }
  public void setSupportsStatistics(boolean b) {
		  Dispatcher.getInstance().getNavajoConfig().setStatisticsRunnerEnabled(b);
  }
  public boolean getSupportsLocks() {
	  return supportsLocks;
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
    return NavajoClassLoader.getInstances();
  }
  public int getScriptClassInstances() {
	return GenericHandler.getLoadedClassesSize();
  }
  public String getServerId() {
    return serverId;
  }

  public int getRequestRateWindowSize(){
    return Dispatcher.rateWindowSize;
  }

  public boolean getMonitorOn() {
    return Dispatcher.getInstance().getNavajoConfig().isMonitorOn();
  }

  /**
   * Sets the full log monitor to on for all requests.
   *
   * @param monitorOn
   */
  public void setMonitorOn(boolean monitorOn) {
    System.err.println("Setting monitor to: " + monitorOn);
    this.monitorOn = monitorOn;
    Dispatcher.getInstance().getNavajoConfig().setMonitorOn(monitorOn);
  }

  public int getMonitorTotaltime() {
    return Dispatcher.getInstance().getNavajoConfig().getMonitorExceedTotaltime();
  }

  public String getMonitorUsers() {
    return Dispatcher.getInstance().getNavajoConfig().getMonitorUsers();
  }

  public String getMonitorWS() {
    return Dispatcher.getInstance().getNavajoConfig().getMonitorWebservices();
  }

  public void setMonitorWS(String monitorWS) {
    this.monitorWS = monitorWS;
    Dispatcher.getInstance().getNavajoConfig().setMonitorWebservices(monitorWS);
  }

  public void setMonitorUsers(String monitorUsers) {
    this.monitorUsers = monitorUsers;
    Dispatcher.getInstance().getNavajoConfig().setMonitorUsers(monitorUsers);
  }

  public void setMonitorTotaltime(int monitorTotaltime) {
    this.monitorTotaltime = monitorTotaltime;
    Dispatcher.getInstance().getNavajoConfig().setMonitorExceedTotaltime(monitorTotaltime);
  }

  public void setWebservice(String w) {
	  this.webservice = w;
  }

//  public WebserviceAccess getWebserviceAccess() {
//	  if ( webservice == null ) {
//		  return null;
//	  }
//	  return WebserviceAccessListener.getInstance().getAccessInfo(webservice);
//  }

  public String getDocumentClass() {
	  return NavajoFactory.getInstance().getClass().getName();
  }

  public void setResetAdapters(boolean b) {
	  this.resetAdapters = b;
	  if ( resetAdapters ) {
		  System.err.println("Resetting adapters...");
		  NavajoConfig.getInstance().doClearCache();
	  }
  }
  
  public void setDocumentClass(String s) throws UserException {
	  try {
		  Class.forName(s);
	  } catch (Exception e) {
		  throw new UserException(-1, "Could not find document implementation: " + s);
	  }
	  System.setProperty("com.dexels.navajo.DocumentImplementation", s);
	  NavajoFactory.resetImplementation();
	  NavajoFactory.getInstance().setTempDir(Dispatcher.getInstance().getTempDir());
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
	  System.err.println("Document class is now: " + getDocumentClass());
  }
  
  public ClassCount [] getClassCounts() {
	  ClassCount [] cc = new ClassCount[1];
	  cc[0].className = "com.dexels.navajo.server.Dispatcher";
	  cc[0].count = com.dexels.navajo.server.Dispatcher.getInstances();
	  return cc;
  }
  
  public void setCollectGarbage(boolean b) {
	  System.err.println("Calling garbage collect");
	  System.gc();
	  System.err.println("Finished collecting garbage");
  }
}
