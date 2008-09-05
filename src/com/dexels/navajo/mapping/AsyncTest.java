package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;

public class AsyncTest extends AsyncMappable {

  public double result = 0.0;
  public double d = 1.0;
  public int iter = 1000000;

  private float ready = (float) 0.0;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    System.out.println("in AsyncTest load()");
  }

  public void kill() {
    System.out.println("in AsyncTest kill()");
  }

  public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    System.out.println("in AsyncTest store()");
  }

  public void setIter(int i) {
    this.iter = i;
  }

  public void setD(double d) {
    System.out.println("in AsyncTest setD(), d = " + d);
    this.d = d;
  }

  public double getResult() {
    System.out.println("in AsyncTest getResult()");
    return result;
  }

  public void run() throws com.dexels.navajo.server.UserException {
      System.out.println("in AsyncTest run()");
      double a = 1000000000.0;
      for (int i = 0; i < iter; i++) {
        a = a/d;
        ready = (float) i / (float) (iter+1) * 100;
        if (this.isStopped()) {
          System.out.println("KILLING THREAD...");
          i = iter + 1;
        } else if (this.isInterrupted()) {
          goToSleep();
        }
        if (i % 1000000 == 0)
          System.out.print(".");
        result = a;
      }
      System.out.println("leaving AsyncTest run()");
  }

  public int getPercReady() {
    return (int) ready;
  }

  public void afterRequest() {
    System.out.println("AsyncTest: in afterReqeust()");
  }

  public void beforeResponse(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) {
    System.out.println("AsyncTest: in beforeResponse()");
  }

  public void afterResponse() {
    // Wait for couple of seconds.
    System.out.println("AsyncTest: in afterResponse()");
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}