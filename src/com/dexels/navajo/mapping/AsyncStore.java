package com.dexels.navajo.mapping;

import java.util.*;
import com.dexels.navajo.server.Access;

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

public final class AsyncStore implements Runnable {

  private static AsyncStore instance = null;
  public HashMap objectStore = null;
  public HashMap accessStore = null;
  private float timeout;
  private long threadWait = 20000;

  public final static AsyncStore getInstance() {
    return instance;
  }

  public final static AsyncStore getInstance(float timeout) {
    if (instance == null) {
      instance = new AsyncStore();
      instance.timeout = timeout;
      if (instance.timeout < instance.threadWait)
        instance.threadWait = (long) (instance.timeout / 2);
      instance.objectStore = new HashMap();
      instance.accessStore = new HashMap();
      Thread thread = new Thread(instance);
      thread.start();
      System.err.println("CREATED ASYNCSTORE, TIMEOUT = " + instance.timeout + ", THREAD WAIT TIMEOUT = " + instance.threadWait);
    }
    return instance;
  }

  public final void run() {
    System.err.println("Started garbage collect thread for async store...");
    long maxAge;
    try {
      while (true) {
        synchronized ( instance ) {
          wait(threadWait);
          Set s = new HashSet(objectStore.keySet());
          Iterator iter = s.iterator();
          while (iter.hasNext()) {
            String ref = (String) iter.next();
            AsyncMappable a = (AsyncMappable) objectStore.get(ref);
            long now = System.currentTimeMillis();
            if ( (now - a.getLastAccess()) > timeout || a.isKilled()) {
              if (!a.isKilled())
                System.err.println("REMOVED " + ref + " FROM OBJECT STORE DUE TO TIME-OUT, now = " + now + ", lastAccess() = " + a.getLastAccess() + ", timeout = " + timeout);
              else
                System.err.println("REMOVED " + ref + " FROM OBJECT STORE DUE TO KILLONFINNISH");
              a.kill();
              objectStore.remove(ref);
              accessStore.remove(ref);
              a = null;
            }
          }
        }
      }
    }
    catch (InterruptedException e) {
    }
  }

  public final String addInstance(AsyncMappable o, Access a) {
    String ref = o.hashCode()+"";
    objectStore.put(ref+"", o);
    accessStore.put(ref+"", a);
    return ref;
  }

  public final Access getAccessObject(String ref) {
    Object o = accessStore.get(ref);
    if (o == null)
      return null;
    else
      return (Access) o;
  }

  public final AsyncMappable getInstance(String ref) {
    Object o = objectStore.get(ref);
    if (o == null)
      return null;
    else
      return (AsyncMappable) o;
  }

  public final synchronized void removeInstance(String ref) {
    Object o = objectStore.get(ref);
    if (o == null)
      return;
    else {
      objectStore.remove(ref);
      if (accessStore.containsKey(ref))
        accessStore.remove(ref);
      o = null;
      System.out.println("REMOVED ASYNC INSTANCE... " + ref + ", WAITING FOR CLEANUP BY GARBAGE COLLECTOR! ");
    }
  }

}