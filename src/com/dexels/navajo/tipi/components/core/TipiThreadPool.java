package com.dexels.navajo.tipi.components.core;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiThreadPool {
  private int poolSize = 1;
  private final ThreadGroup myGroup = new ThreadGroup("TipiThreadGroup");
// private final Set myThreadSet = Collections.synchronizedSet(new HashSet());
  private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
  private final TipiContext myContext;
  private final Map myListenerMap = Collections.synchronizedMap(new HashMap());
  private List myThreadCollection = Collections.synchronizedList(new ArrayList());
  public TipiThreadPool(TipiContext context,int poolSize) {
    this.poolSize = poolSize;
    myContext = context;
    String maxThreads = System.getProperty("com.dexels.navajo.tipi.maxthreads");
    if (maxThreads != null && !"".equals(maxThreads)) {
      int i = Integer.parseInt(maxThreads);
//      System.err.println("Using maxthread: " + i);
      poolSize = i;
    }
    for (int i = 0; i < poolSize; i++) {
      createThread("TipiThread #" + i);
    }
  }

  private final  void createThread(String name) {
    TipiThread tt = new TipiThread(name, myGroup, this);
    myThreadCollection.add(tt);
    tt.start();
  }

  public synchronized TipiEvent getExecutable() {
    if (myWaitingQueue.size() == 0) {
      return null;
    }
    TipiEvent te = (TipiEvent) myWaitingQueue.get(0);
    myWaitingQueue.remove(0);
    return te;
  }


  public TipiContext getContext() {
    return myContext;
  }

  public synchronized TipiEvent blockingGetExecutable() {
    while (true) {
      TipiEvent te = getExecutable();
      if (te == null) {
        try {
          myContext.threadEnded(Thread.currentThread());
          wait();
        }
        catch (InterruptedException ex) {
          System.err.println("interrupted");
        }
      }
      else {
        return te;
      }
    }
//    return null;
  }

  private synchronized void enqueueExecutable(TipiEvent te) {
     myWaitingQueue.add(te);
     notify();
   }

  public void init(int maxpoolSize) {
    poolSize = maxpoolSize;
  }

  public TipiEventListener getEventListener(TipiExecutable te) {
    return (TipiEventListener)myListenerMap.get(te);
  }

  public void removeEventListener(TipiEvent te) {
    myListenerMap.remove(te);
  }

  public synchronized void performAction(final TipiEvent te, final TipiEventListener listener) {
    myListenerMap.put(te,listener);
    System.err.println(">>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>Enqueueing exe, myListenerMap is " + myListenerMap.size());
    enqueueExecutable(te);
  }
//  public synchronized Thread performAction(final TipiEvent te) {
//    Thread t = new Thread(myGroup, new Runnable() {
//      public void run() {
//        try {
//          te.performAction();
//         myContext.threadEnded(te, Thread.currentThread());
//        }
//        catch (Exception ex) {
//          ex.printStackTrace();
//        }
//        finally {
//          System.err.println("Freeing thread");
//        }
//      }
//    },"TipiThread");
//    System.err.println("Thread deployed successfully");
//  }
//
}
