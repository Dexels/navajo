package com.dexels.navajo.server.statistics;

import java.util.HashSet;
import com.dexels.navajo.server.Access;
import java.util.Set;
import java.util.Iterator;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class StatisticsRunner implements Runnable {

  private static StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  private HashSet todo = new HashSet();

  public final static StatisticsRunner getInstance(String storePath) {
    if (instance == null) {
      instance = new StatisticsRunner();
      instance.myStore = new com.dexels.navajo.server.statistics.HSQLStore(storePath);
      Thread thread = new Thread(instance);
      thread.start();
      System.err.println("Started StatisticsRunner version $Id$");
    }
    return instance;
  }

  public void run() {
    try {
      while (true) {
        synchronized (instance) {
          //System.err.println("Entering StatisticsRunner thread....");
          wait(10000);
          // Check for new access objects.
          Set s = new HashSet( (HashSet) todo.clone());
          Iterator iter = s.iterator();
          while (iter.hasNext()) {
            Access tb = (Access) iter.next();
            //System.err.println("Processing access object: " + tb.accessID);
            myStore.storeAccess(tb);
            todo.remove(tb);
            tb = null;
            if (todo.size() > 100) {
              System.err.println("WARNING TODO list size:  " + todo.size());
            }
            Thread.yield();
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public synchronized void addAccess(Access a) {
    //System.err.println("Adding access object: " + a.accessID);
    todo.add(a);
  }

}