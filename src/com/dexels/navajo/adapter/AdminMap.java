package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
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
  public AccessMap [] users;
  public AsyncProxy [] asyncThreads;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }

  /**
    * Some admin functions.
    *
    * @param datasourceName
    * @throws MappableException
    * @throws UserException
    */
   public final int getOpenConnections(String datasource) {
       SQLMap sql = new SQLMap();

       if (sql.fixedBroker != null && sql.getUsername() != null && sql.getPassword() != null &&
           sql.fixedBroker.get(datasource, sql.getUsername(), sql.getPassword()) != null) {
         return (sql.fixedBroker.get(datasource, sql.getUsername(),
                                     sql.getPassword()).getUseCount());
       } else
         return 0;
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
        am.userName = a.rpcUser;
        am.webService = a.rpcName;
        am.created = a.created;
        d.add(am);
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


}