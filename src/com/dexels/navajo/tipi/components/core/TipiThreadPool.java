package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.tipi.*;
import java.util.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiThreadPool {

  private int poolSize = 2;

 private final ThreadGroup myGroup = new ThreadGroup("TipiThreadGroup");
// private final Set myThreadSet = Collections.synchronizedSet(new HashSet());
 private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
 private final TipiContext myContext;

 private List myThreadCollection = Collections.synchronizedList(new ArrayList());

  public TipiThreadPool(TipiContext context) {
    myContext = context;
    String maxThreads = System.getProperty("com.dexels.navajo.tipi.maxthreads");
    if (maxThreads!=null && !"".equals(maxThreads)) {
      int i = Integer.parseInt(maxThreads);
      System.err.println("Using maxthread: "+i);
      poolSize = i;
    }
    for (int i = 0; i < poolSize; i++) {
      createThread("TipiThread #"+i);
    }
  }

  private void createThread(String name) {
    TipiThread tt = new TipiThread(name, myGroup,this);
    myThreadCollection.add(tt);
    tt.start();
  }

  public synchronized TipiExecutable getExecutable() {
    if (myWaitingQueue.size()==0) {
      return null;
    }
    TipiExecutable te =  (TipiExecutable) myWaitingQueue.get(0);
    myWaitingQueue.remove(0);
    return te;
  }

  public synchronized TipiExecutable blockingGetExecutable() {
    while (true) {
      TipiExecutable te = getExecutable();
      if (te==null) {
        try {
          System.err.println("Thread name: "+((TipiThread)Thread.currentThread()).getName()+" is waiting");
          wait();
        }
        catch (InterruptedException ex) {
          System.err.println("interrupted");
        }
        System.err.println("Thread name: "+((TipiThread)Thread.currentThread()).getName()+" Continuing after wait()");
      } else {
        return te;
      }
    }
//    return null;
  }

  private synchronized void enqueueExecutable(TipiExecutable te) {
    myWaitingQueue.add(te);
    notify();
  }

  public void init(int maxpoolSize) {
    poolSize = maxpoolSize;
  }

  public synchronized void performAction(final TipiEvent te) {
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
