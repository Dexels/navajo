package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
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
public class TipiPerformMethod
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    TipiValue dest = getParameter("destination");
    TipiValue bon = getParameter("breakOnError");
    boolean breakOnError = false;
    long expirationInterval = -1;
    Operand brk = getEvaluatedParameter("breakOnError",event);
      if (brk!=null) {
        breakOnError = ((Boolean)brk.value).booleanValue();
      }
    String destination = (String) getParameter("destination").getValue();
    if (destination == null) {
      destination = "*";
    }

    Operand expiration = getEvaluatedParameter("expirationInterval",event);
      if (expiration!=null) {
//        System.err.println("Expirationclass: "+expiration.value.getClass());
        expirationInterval = ((Integer)expiration.value).intValue();
      }


    /** @todo REWRITE THIS STRANGE CONSTRUCTION. LOOKS OLD. SHOULD BE MUCH EASIER NOW */
    TipiValue sourceTipi = getParameter("tipipath");
    Operand method = getEvaluatedParameter("method",event);
    TipiDataComponent evalTipi = null;
//    String evalMethod = null;
    try {
      evalTipi = (TipiDataComponent) evaluate(sourceTipi.getValue(),event).value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException("Can not evaluate tipi path expression. Switching to manual.");
    }
    if (method == null) {
      throw new IllegalArgumentException("Error performing method. Method evaluated to null.");
    }
    // if no sending tipi has been specified:
    if (sourceTipi == null || "".equals(sourceTipi)) {
      // If it can not even find a suitable Navajo, just create a blank one
      if (myComponent.getNearestNavajo() != null) {
        myContext.performTipiMethod(null, myComponent.getNearestNavajo(), destination, method.value.toString(),breakOnError,event,expirationInterval);
      }
      // use the closest navajo
      else {
        myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(), destination, method.value.toString(),breakOnError,event,expirationInterval);
      }
      return;
    }
    // If it can not find the 'sending' tipi:
    if (evalTipi == null) {
      if (myComponent.getNearestNavajo() != null) {
        Navajo n = myComponent.getNearestNavajo();
//        System.err.println("Not a blank NAvajo!!!");
        myContext.performTipiMethod(null, n, destination, method.value.toString(),breakOnError,event,expirationInterval);
      }
      else {
        System.err.println("Could not evaluate tipi. Calling service with blank navajo");
        myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(), destination, method.value.toString(),breakOnError,event,expirationInterval);
      }
      return;
    }
    // When there is a sending tipi, just perform it:
    evalTipi.performService(myContext, destination, method.value.toString(),breakOnError,event,expirationInterval);

  }
}
