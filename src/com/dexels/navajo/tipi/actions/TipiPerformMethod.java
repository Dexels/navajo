package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.document.*;

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
    TipiValue dest = getParameter("destination");
    String destination = (String) getParameter("destination").getValue();
    if (destination==null) {
      destination="*";
    }
    TipiValue sourceTipi = getParameter("tipipath");
    TipiValue method = getParameter("method");
    if (sourceTipi==null) {
      myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(),"*",method.getValue());
      return;
    }

    TipiPathParser pp = new TipiPathParser(myComponent, myContext, sourceTipi.getValue());
    Tipi t = pp.getTipi();
    if (t == null) {
      myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(),"*",method.getValue());
     return;
    }
      t.performService(myContext, destination, method.getValue());
  }
}