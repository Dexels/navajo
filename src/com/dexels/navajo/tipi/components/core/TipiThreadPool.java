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
  private int poolSize = 10;
  private final ThreadGroup myGroup = new ThreadGroup("TipiThreadGroup");
// private final Set myThreadSet = Collections.synchronizedSet(new HashSet());
  private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
  private final TipiContext myContext;
  private final Map myListenerMap = Collections.synchronizedMap(new HashMap());
  private List myThreadCollection = Collections.synchronizedList(new ArrayList());
  public TipiThreadPool(TipiContext context) {
    myContext = context;
    String maxThreads = System.getProperty("com.dexels.navajo.tipi.maxthreads");
    if (maxThreads != null && !"".equals(maxThreads)) {
      int i = Integer.parseInt(maxThreads);
      System.err.println("Using maxthread: " + i);
      poolSize = i;
    }
    for (int i = 0; i < poolSize; i++) {
      createThread("TipiThread #" + i);
    }
  }

  private void createThread(String name) {
    TipiThread tt = new TipiThread(name, myGroup, this);
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

  public void write(String s) {
    try {
      FileWriter fw = new FileWriter("ThreadAccess.txt", true);
      fw.write(s + "\r\n");
      fw.flush();
      fw.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public TipiContext getContext() {
    return myContext;
  }

  public synchronized TipiExecutable blockingGetExecutable() {
    while (true) {
      TipiExecutable te = getExecutable();
      if (te == null) {
        try {
          write("Thread name: " + ( (TipiThread) Thread.currentThread()).getName() + " is waiting");
          myContext.threadEnded(Thread.currentThread());
          wait();
        }
        catch (InterruptedException ex) {
          System.err.println("interrupted");
        }
        write("Thread name: " + ( (TipiThread) Thread.currentThread()).getName() + " Continuing after wait()");
      }
      else {
        return te;
      }
    }
//    return null;
  }

  private synchronized void enqueueExecutable(TipiExecutable te) {
    write("Executable enqueued");
    myWaitingQueue.add(te);
    notify();
  }

  public void init(int maxpoolSize) {
    poolSize = maxpoolSize;
  }

  public TipiEventListener getEventListener(TipiExecutable te) {
    return (TipiEventListener)myListenerMap.get(te);
  }

  public synchronized void performAction(final TipiEvent te, final TipiEventListener listener) {
    myListenerMap.put(te,listener);
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
