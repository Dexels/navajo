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
  private final TipiContext myContext;
  public TipiThread(TipiContext context, String name, ThreadGroup group, TipiThreadPool tp) {
    super(group, name);
    setDaemon(true);
    myName = name;
    myPool = tp;
    myContext = context;
  }

  public void run() {
    while (true) {
      try {
        try {
          while (true) {
              TipiExecutable te = myPool.blockingGetExecutable();
            myContext.debugLog("event","Thread: "+myName+" got an executable. Performing now");
            try {
              myPool.getContext().threadStarted(Thread.currentThread());
              te.performAction(te.getEvent());
//             te.performAction(te.getEvent());
//           System.err.println("Thread: "+myName+" finished");
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
            finally {
              TipiEventListener tel = myPool.getEventListener(te);
              if (tel != null) {
                tel.eventFinished(te, null);
              }
              if (te.getComponent()!=null) {
                te.getComponent().eventFinished(te, te);
            }
              myPool.removeEventListener(te.getEvent());
            }
          }
        }
        finally {
          System.err.println("Ayyyyy this thread is dying!");
          myPool.getContext().threadEnded(Thread.currentThread());
        }
      }
      catch (ThreadShutdownException t) {
        System.err.println("Thread received a shutdown request. Farewell..");
        return;
      }
      catch (Throwable t) {
        System.err.println("Caught uncaught exception in thread:");
        t.printStackTrace();
        System.err.println("Reviving dying thread...");
      }
    }
  }
}
