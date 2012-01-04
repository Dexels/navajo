package com.dexels.navajo.client.queueimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

public final class ThreadPool {

  private final static int THREAD_COUNT = 5;

  private final List<Runnable> myWaitingQueue = Collections.synchronizedList(new ArrayList<Runnable>());
  private Set<Thread> activeThreadSet = Collections.synchronizedSet(new HashSet<Thread>());
  private List<PoolThread> myThreadCollection = Collections.synchronizedList(new ArrayList<PoolThread>());
  private ThreadGroup tg = new ThreadGroup("navajo");
  
  private final static Logger logger = LoggerFactory.getLogger(ThreadPool.class);
  
  public ThreadPool(final ClientQueueImpl client) {
     for (int i = 0; i < THREAD_COUNT; i++) {
      PoolThread p = new PoolThread("navajoThread_" + i, tg, this);
      myThreadCollection.add(p);
      p.start();
    }
  }

  private synchronized Runnable getExecutable() {
    if (myWaitingQueue.size() == 0) {
      return null;
    }
    Runnable te = myWaitingQueue.get(0);
    myWaitingQueue.remove(0);
    return te;
  }

  public synchronized Runnable blockingGetExecutable() {
    while (true) {
      Runnable te = getExecutable();
      if (te == null) {
        try {
          activeThreadSet.remove(Thread.currentThread());

          if(getQueueSize() == 0 && getActiveThreads() ==0){
          }
          wait();
        }
        catch (InterruptedException ex) {
          logger.info("interrupted");
        }
      }
      else {
        activeThreadSet.add(Thread.currentThread());
        return te;
      }
    }
  }

  public synchronized void enqueueExecutable(Runnable te, String method) {
    myWaitingQueue.add(te);
    notify();
  }

  public int getQueueSize() {
    return myWaitingQueue.size();
  }

  public int getActiveThreads() {
    return activeThreadSet.size();
  }

  public void destroy() {
	  for (Iterator<PoolThread> iter = myThreadCollection.iterator(); iter.hasNext();) {
		PoolThread element = iter.next();
		element.stopPoolThread();
	}
  }
}
