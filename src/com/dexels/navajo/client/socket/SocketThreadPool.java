package com.dexels.navajo.client.socket;
import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class SocketThreadPool {
  private int poolSize = 1;
  private final ThreadGroup myGroup = new ThreadGroup("TipiThreadGroup");
// private final Set myThreadSet = Collections.synchronizedSet(new HashSet());
  private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
  private final NavajoSocketListener myListener;
  private final Map myListenerMap = Collections.synchronizedMap(new HashMap());
  private List myThreadCollection = Collections.synchronizedList(new ArrayList());

  private boolean running = true;
  // for use with echo

  public SocketThreadPool(NavajoSocketListener listener,int initSize) {
    this.poolSize = initSize;
    myListener = listener;
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
      createThread("NavajoSocketThread #" + i);
    }
    System.err.println("Available threads: "+myThreadCollection.size());
  }

  private final  void createThread(String name) {
    SocketThread tt = new SocketThread(myListener,name, myGroup, this);
//    System.err.println("Creating thread: "+name);
     myThreadCollection.add(tt);
    tt.start();
  }

  public synchronized SocketConnection getExecutable() {
    if (myWaitingQueue.size() == 0) {
      return null;
    }
    SocketConnection te = (SocketConnection) myWaitingQueue.get(0);
    myWaitingQueue.remove(0);
    return te;
  }


  public NavajoSocketListener getContext() {
    return myListener;
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
      SocketThread item = (SocketThread)iter.next();
//      item.shutdown();
      item.interrupt();
    }
  }


  public synchronized SocketConnection blockingGetExecutable() {
    while (isRunning()) {
        SocketConnection te = getExecutable();
      if (te == null) {
        try {
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
    return null;
  }
  public void init(int maxpoolSize) {
    poolSize = maxpoolSize;
  }

 
  public synchronized void enqueueExecutable(SocketConnection exe)  {
        myWaitingQueue.add(exe);
        notify();
     }


}
