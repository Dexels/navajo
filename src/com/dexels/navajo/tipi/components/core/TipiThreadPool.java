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

  private boolean running = true;
  // for use with echo

  public TipiThreadPool(TipiContext context,int initSize) {
    this.poolSize = initSize;
    myContext = context;
    String maxThreads = System.getProperty("com.dexels.navajo.tipi.maxthreads");
//    System.err.println("MAX THREADS::::::: "+maxThreads+"\n\n"+" poolsize: "+initSize);

    // HACK!
//    this.poolSize = 1;

    if (maxThreads != null && !"".equals(maxThreads)) {
      int i = Integer.parseInt(maxThreads);
//      System.err.println("Using maxthread: " + i);
      this.poolSize = i;
    }
    for (int i = 0; i < this.poolSize; i++) {
      createThread("TipiThread #" + i);
    }
//    System.err.println("Available threads: "+myThreadCollection.size());
  }

  private final  void createThread(String name) {
    TipiThread tt = new TipiThread(myContext,name, myGroup, this);
//    System.err.println("Creating thread: "+name);
     myThreadCollection.add(tt);
    tt.start();
  }

  public synchronized TipiExecutable getExecutable() {
    if (myWaitingQueue.size() == 0) {
      return null;
    }
    TipiExecutable te = (TipiExecutable) myWaitingQueue.get(0);
    myWaitingQueue.remove(0);
    return te;
  }


  public TipiContext getContext() {
    return myContext;
  }

  private synchronized boolean isRunning() {
    return running;
  }

  private synchronized void setRunning(boolean r) {
    running = r;
  }

  public void shutdown() {
    setRunning(false);
    for (Iterator iter = myThreadCollection.iterator(); iter.hasNext(); ) {
      TipiThread item = (TipiThread)iter.next();
//      item.shutdown();
      item.interrupt();
    }
  }


  public synchronized TipiExecutable blockingGetExecutable() throws ThreadShutdownException {
    while (isRunning()) {
        TipiExecutable te = getExecutable();
      if (te == null) {
        try {
          myContext.threadEnded(Thread.currentThread());
          wait();
        }
        catch (InterruptedException ex) {
//          System.err.println("interrupted");
        }
      }
      else {
        return te;
      }
    }
    throw new ThreadShutdownException();
//    return null;
  }

//  private synchronized void enqueueExecutable(TipiEvent te) throws  TipiException {
//    if (poolSize==0) {
//             te.performAction((TipiEvent)te);
//    }
//    else {
//      myWaitingQueue.add(te);
//      notify();
//    }
//   }


  public TipiEventListener getEventListener(TipiExecutable te) {
    return (TipiEventListener)myListenerMap.get(te);
  }

  public void removeEventListener(TipiEvent te) {
    myListenerMap.remove(te);
  }

  public void enqueueExecutable(TipiExecutable exe) throws  TipiException {
	  if (poolSize==0) {
          // For echo:
          exe.getEvent().performAction(exe.getEvent(),exe.getEvent(),0);
      }
      else {
        myWaitingQueue.add(exe);
        awaken();
      }
     }

  
  
  private synchronized void awaken() {
//      for (Iterator iter = myThreadCollection.iterator(); iter.hasNext();) {
//        TipiThread element = (TipiThread) iter.next();
//        element.interrupt();
//    }
	  notifyAll();
}

public void performAction(final TipiEvent te, final TipiEventListener listener) throws TipiException {
    myListenerMap.put(te,listener);
//    System.err.println(">>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>Enqueueing exe, myListenerMap is " + myListenerMap.size()+" thread: "+Thread.currentThread().getName());

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
