package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;

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
    Operand method = getEvaluatedParameter("method");
    Tipi evalTipi = null;
//    String evalMethod = null;

    try {
      evalTipi = (Tipi) evaluate(sourceTipi.getValue()).value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      System.err.println("Can not evaluate tipi path expression. Switching to manual.");
      TipiPathParser pp = new TipiPathParser(myComponent, myContext, sourceTipi.getValue());
      evalTipi = pp.getTipi();
    }

//    try {
//      evalMethod = (String) evaluate(method.getValue()).value;
//    }
//    catch (Exception ex1) {
//      evalMethod = method.getValue();
//    }
    if (method==null) {
      throw new IllegalArgumentException("Error performing method. Method evaluated to null.");
    }


    if (sourceTipi==null) {
      myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(),"*",method.value.toString());
      return;
    }

    if (evalTipi == null) {
      System.err.println("Could not evaluate tipi. Calling service with blank navajo");
      myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(),"*",method.value.toString());
     return;
    }
      evalTipi.performService(myContext, destination, method.value.toString());
  }
}