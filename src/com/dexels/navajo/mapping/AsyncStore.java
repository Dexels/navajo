package com.dexels.navajo.mapping;

import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * This (singleton) object is used to store instances of (active) asynchronous mappable objects.
 * An instance is de-activated if the store() or kill() methods is called or if a certain time-out is reached.
 */

public class AsyncStore implements Runnable {

  private static AsyncStore instance = null;
  private HashMap objectStore = null;
  private float timeout;
  private long threadWait = 20000;

  public static AsyncStore getInstance(float timeout) {
    if (instance == null) {
      instance = new AsyncStore();
      instance.timeout = timeout;
      if (instance.timeout < instance.threadWait)
        instance.threadWait = (long) (instance.timeout / 2);
      instance.objectStore = new HashMap();
      Thread thread = new Thread(instance);
      thread.start();
      System.out.println("CREATED ASYNCSTORE, TIMEOUT = " + instance.timeout + ", THREAD WAIT TIMEOUT = " + instance.threadWait);
    }
    return instance;
  }

  public void run()
  {
      System.out.println("Started garbage collect thread for async store...");
      long maxAge;
      try {
	while(true) {
	  synchronized(this) {
	    wait(threadWait);
            Iterator iter = objectStore.keySet().iterator();
            while (iter.hasNext()) {
              String ref = (String) iter.next();
              AsyncMappable a = (AsyncMappable) objectStore.get(ref);
              long now = System.currentTimeMillis();
              if ((now - a.getLastAccess()) > timeout) {
                a.kill();
                objectStore.remove(ref);
                a = null;
                System.out.println("REMOVED " + ref + " FROM OBJECT STORE DUE TO TIME-OUT");
              }
            }
	  }
	}
      } catch(InterruptedException e) {
      }
  }

  public String addInstance(AsyncMappable o) {
    String ref = o.hashCode()+"";
    objectStore.put(ref+"", o);
    return ref;
  }

  public AsyncMappable getInstance(String ref) {
    Object o = objectStore.get(ref);
    if (o == null)
      return null;
    else
      return (AsyncMappable) o;
  }

  public void removeInstance(String ref) {
    Object o = objectStore.get(ref);
    if (o == null)
      return;
    else {
      objectStore.remove(ref);
      o = null;
      System.out.println("REMOVED ASYNC INSTANCE... WAITING FOR CLEANUP BY GARBAGE COLLECTOR! ");
    }
  }

}