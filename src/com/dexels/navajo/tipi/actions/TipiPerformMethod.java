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
    TipiValue dest = getParameter("destination");
    String destination = (String) getParameter("destination").getValue();
    if (destination==null) {
      destination="*";
    }
    TipiValue sourceTipi = getParameter("tipipath");
    TipiValue method = getParameter("method");
    Tipi evalTipi = null;
    String evalMethod = null;

    try {
      evalTipi = (Tipi) evaluate(sourceTipi.getValue()).value;
    }
    catch (Exception ex) {
      System.err.println("Can not evaluate tipi path expression. Switching to manual.");
      TipiPathParser pp = new TipiPathParser(myComponent, myContext, sourceTipi.getValue());
      evalTipi = pp.getTipi();
    }

    try {
      evalMethod = (String) evaluate(method.getValue()).value;
    }
    catch (Exception ex1) {
      evalMethod = method.getValue();
    }

    if (sourceTipi==null) {
      myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(),"*",method.getValue());
      return;
    }

    if (evalTipi == null) {
      System.err.println("Could not evaluate tipi. Calling service with blank navajo");
      myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(),"*",method.getValue());
     return;
    }
      evalTipi.performService(myContext, destination, evalMethod);
  }
}