package com.dexels.navajo.tipi.components.core;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiThread
    extends Thread {
  private final TipiThreadPool myPool;
  private final List myActivities = new ArrayList();
  private final String myName;
  public TipiThread(String name, ThreadGroup group, TipiThreadPool tp) {
    super(group, name);
    myName = name;
    myPool = tp;
  }

  public void run() {
    try {
    while(true) {
        try {
          while (true) {
            TipiEvent te = myPool.blockingGetExecutable();
//        myPool.write("Thread: "+myName+" got an executable. Performing now");
            try {
              myPool.getContext().threadStarted(Thread.currentThread());
              te.performAction(te);
//           myPool.write("Thread: "+myName+" finished");
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
            finally {
              TipiEventListener tel = myPool.getEventListener(te);
              if (tel != null) {
                tel.eventFinished(te, null);
              }
            }
          }
        }
        finally {
//      myPool.write("ARRRGGGGG THis thread "+myName+"is dying!");
          System.err.println("ARRRGGGGG THis thread is dying!");
          myPool.getContext().threadEnded(Thread.currentThread());
        }
      }
    } catch(Throwable t) {
      System.err.println("Caught uncaught exception in thread.");
      t.printStackTrace();
      System.err.println("Reviving dying thread...");
    }
  }

//  public void setThreadBusy(boolean b) {
//  }

//  public void performActivity() throws TipiBreakException, TipiException {
//    if (myActivities.size() == 0) {
//      return;
//    }
//    TipiExecutable te = (TipiExecutable) myActivities.get(0);
//    te.performAction();
//  }
//
//  public void addExecutable(TipiExecutable te) {
//  }
}
