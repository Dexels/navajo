package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.tipi.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiThread extends Thread {

  private final TipiThreadPool myPool;
  private final List myActivities = new ArrayList();

  public TipiThread(TipiThreadPool tp) {
    myPool = tp;
  }

  public void run() {
    while (true) {
      try {
        setThreadBusy(true);
//        performActivity();
        setThreadBusy(false);
        wait();
      }
      catch (InterruptedException ex) {
        System.err.println("Interrupted");
      }
    }
  }

  public void setThreadBusy(boolean b) {

  }

  public void performActivity() throws TipiBreakException, TipiException {
    if (myActivities.size()==0) {
      return;
    }
    TipiExecutable te = (TipiExecutable)myActivities.get(0);
    te.performAction();
  }


  public void addExecutable(TipiExecutable te) {

  }
}
