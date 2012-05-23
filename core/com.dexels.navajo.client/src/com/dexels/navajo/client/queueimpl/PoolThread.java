package com.dexels.navajo.client.queueimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  
  private final static Logger logger = LoggerFactory.getLogger(PoolThread.class);

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
        logger.info("Caught uncaught exception in thread:");
        logger.error("Error: ", t);
        logger.info("Reviving dying thread...");
      }
    }
    logger.info("Thread: " + getName()+" is dying! Goodbye!!!!!");
  }
  
  public void stopPoolThread() {
	  running = false;
	  interrupt();
  }
  
}
