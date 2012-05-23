package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.AsyncMappable;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AsyncProxy implements Mappable {

  public boolean isFinished = false;
  public boolean killOnFinnish = false;
  private Exception caught = null;
  public long startTime = System.currentTimeMillis();
  public long lastAccess = System.currentTimeMillis();
  public int totaltime;
  public String name;
  public String pointer;
  public java.util.Date startDate;
  public boolean kill = false;
  public int percReady;
  public boolean running = false;
  public boolean interrupt = false;
  public boolean resume = false;
  public String user;
  public String rpcName;
  public String accessId;
  public String ipAddress;
  public String host;
  public String stackTrace;
  public String threadName;
  public boolean waiting;
  public String lockName;
  public String lockOwner;
  public String lockClass;

  public void load(Access access) throws MappableException, UserException {
    //System.err.println("IN ASYNCPROXY LOAD()......................");
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {

  }
  
  public String getAccessId() {
    return accessId;
  }

  public void setResume(boolean b) {
   //System.err.println("..............................CALLING SETRESUME ON ASYNCPROXY WITH POINTER: " + this.pointer);
   if (b) {
    Map all = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore;
    Iterator iter = all.values().iterator();
    AsyncMappable am = null;
    boolean found = false;
    while (iter.hasNext() && !found) {
      am = (AsyncMappable) iter.next();
      if (am.getPointer().equals(this.pointer))
        found = true;
    }
    am.resume();
  }
 }

  public void setInterrupt(boolean b) {
    //System.err.println("..............................CALLING SETINTERRUPT ON ASYNCPROXY WITH POINTER: " + this.pointer);
    if (b) {
     Map all = com.dexels.navajo.mapping.AsyncStore.getInstance().
         objectStore;
     Iterator iter = all.values().iterator();
     AsyncMappable am = null;
     boolean found = false;
     while (iter.hasNext() && !found) {
       am = (AsyncMappable) iter.next();
       if (am.getPointer().equals(this.pointer))
         found = true;
     }
     am.interrupt();
   }
  }

  public void setKill(boolean b) {
    if (b) {
      Map all = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore;
      Iterator iter = all.values().iterator();
      AsyncMappable am = null;
      boolean found = false;
      while (iter.hasNext() && !found) {
        am = (AsyncMappable) iter.next();
        if (am.getPointer().equals(this.pointer))
          found = true;
      }
      am.stop();
      com.dexels.navajo.mapping.AsyncStore.getInstance().removeInstance(pointer);
    }
  }

  public void setPointer(String pointer) {
    //System.err.println("SETTING POINTER TO: " + pointer);
    this.pointer = pointer;
  }
  public java.util.Date getStartDate() {
    return startDate;
  }
  public long getStartTime() {
    return startTime;
  }
  public String getName() {
    return name;
  }
  public long getLastAccess() {
    return lastAccess;
  }

  public boolean getIsFinished() {
    return isFinished;
  }
  public int getPercReady() {
    return percReady;
  }
  public boolean getRunning() {
    return running;
  }
  public boolean getInterrupt() {
    return interrupt;
  }
  public String getPointer() {
    return pointer;
  }
  public String getUser() {
    return user;
  }
  public String getRpcName() {
    return rpcName;
  }
  public int getTotaltime() {
    return totaltime;
  }
  public String getIpAddress() {
    return ipAddress;
  }
  public String getHost() {
    return host;
  }
  public boolean getKill() {
    return kill;
  }

public Exception getCaught() {
	return caught;
}

public boolean isKillOnFinnish() {
	return killOnFinnish;
}

public String getLockClass() {
	return lockClass;
}

public String getLockName() {
	return lockName;
}

public String getLockOwner() {
	return lockOwner;
}

public boolean isResume() {
	return resume;
}

public String getStackTrace() {
	return stackTrace;
}

public String getThreadName() {
	return threadName;
}

public boolean getWaiting() {
	return waiting;
}


}