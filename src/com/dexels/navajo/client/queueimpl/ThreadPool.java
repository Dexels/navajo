package com.dexels.navajo.client.queueimpl;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ThreadPool {

  private final int THREAD_COUNT = 5;

  private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
  private Set activeThreadSet = Collections.synchronizedSet(new HashSet());
  private List myThreadCollection = Collections.synchronizedList(new ArrayList());
  private final ClientQueueImpl client;
  private long startTime = 0;
  private
  ThreadGroup tg = new ThreadGroup("navajo");

  public ThreadPool(final ClientQueueImpl client) {
    this.client = client;
     for (int i = 0; i < THREAD_COUNT; i++) {
      PoolThread p = new PoolThread("navajoThread_" + i, tg, this);
      myThreadCollection.add(myThreadCollection);
      p.start();
    }
  }

  private synchronized Runnable getExecutable() {
    if (myWaitingQueue.size() == 0) {
      return null;
    }
    Runnable te = (Runnable) myWaitingQueue.get(0);
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
            long end = System.currentTimeMillis();
            long millis = end - startTime;
            client.fireActivityChanged(false, null, getQueueSize(), getActiveThreads(), millis);
          }
          wait();
        }
        catch (InterruptedException ex) {
          System.err.println("interrupted");
        }
      }
      else {
        activeThreadSet.add(Thread.currentThread());
        System.err.println("Threads: "+activeThreadSet.toString());
        return te;
      }
    }
  }

  public synchronized void enqueueExecutable(Runnable te, String method) {
    if(getQueueSize() == 0 && getActiveThreads() ==0){
      startTime = System.currentTimeMillis();
    }
    myWaitingQueue.add(te);
    notify();
  }

  public int getQueueSize() {
    return myWaitingQueue.size();
  }

  public int getActiveThreads() {
    return activeThreadSet.size();
  }


}
