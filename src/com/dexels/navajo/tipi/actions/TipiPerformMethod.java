package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiPerformMethod extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    performMethod();
  }

  private void performMethod() throws TipiBreakException, TipiException {
//    String componentPath = (String) myParams.get("tipipath");
//    String method = (String) myParams.get("method");
    TipiValue dest = getParameter("destination");
    String destination = (String) getParameter("destination").getValue();
    if (destination==null) {
      destination="*";
    }

    TipiPathParser pp = new TipiPathParser(myComponent, myContext, getParameter("tipipath").getValue());
    Tipi t = pp.getTipi();
    if (t == null) {
      throw new TipiException("Can not find sourcetipi for: " + getParameter("tipipath").getValue());
    }
//    if (destination==null) {
//      destination="*";
//    }
//
//    try {
      t.performService(myContext, destination, getParameter("method").getValue());
//    }
//    catch (TipiException ex) {
//      System.err.println("Error preforming method!");
//      ex.printStackTrace();
//    }
  }

}