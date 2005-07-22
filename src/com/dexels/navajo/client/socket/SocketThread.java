package com.dexels.navajo.client.socket;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class SocketThread
    extends Thread {
  private final SocketThreadPool myPool;
  private final List myActivities = new ArrayList();
  private final String myName;
  private final NavajoSocketListener myListener;
  public SocketThread(NavajoSocketListener listener, String name, ThreadGroup group, SocketThreadPool tp) {
    super(group, name);
    setDaemon(true);
    myName = name;
    myPool = tp;
    myListener = listener;
  }

  public void run() {
    while (true) {
      try {
           while (true) {
              SocketConnection te = myPool.blockingGetExecutable();
              te.run();
             }
        }
        finally {
          System.err.println("Ayyyyy this thread is dying!");
         }
      }

    }
  }

