package com.dexels.navajo.server.statistics;

import java.util.HashSet;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class StatisticsRunner
    implements Runnable {

  private static StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  private HashSet todo = new HashSet();

  public final static StatisticsRunner getInstance() {
    if (instance == null) {
      instance = new StatisticsRunner();
      instance.myStore = new com.dexels.navajo.server.statistics.HSQLStore();
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
          System.err.println("Entering StatisticsRunner thread....");
          wait(10000);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

}