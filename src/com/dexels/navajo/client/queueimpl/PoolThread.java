package com.dexels.navajo.client.queueimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PoolThread
    extends Thread {
  private final ThreadPool myPool;
  
  boolean running = true;
  public PoolThread(String name, ThreadGroup group, ThreadPool tp) {
    super(group, name);
    setDaemon(true);
    myPool = tp;
  }

  public void run() {
    while (running) {
      try {
        Runnable te = myPool.blockingGetExecutable();
        te.run();
        synchronized (myPool) {
          myPool.notify();
        }
      }
      catch (Throwable t) {
        System.err.println("Caught uncaught exception in thread:");
        t.printStackTrace();
        System.err.println("Reviving dying thread...");
      }
    }
    System.err.println("Thread: " + getName()+" is dying! Goodbye!!!!!");
  }
  
  public void stopPoolThread() {
	  running = false;
	  interrupt();
  }
  
}
