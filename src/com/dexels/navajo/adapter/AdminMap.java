package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.AsyncMappable;
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

public class AdminMap implements Mappable {

  public int openConnections;
  public int requestCount;
  public AccessMap [] users;
  public AsyncMappable [] asyncThreads;

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

   public AsyncMappable [] getAsyncThreads() {
     HashMap all = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore;
     Iterator iter = all.values().iterator();
     ArrayList l = new ArrayList();
     while (iter.hasNext()) {
       AsyncMappable am = (AsyncMappable) iter.next();
       l.add(am);
     }
     AsyncMappable [] objects = new AsyncMappable[l.size()];
     return (AsyncMappable []) l.toArray(objects);
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

}