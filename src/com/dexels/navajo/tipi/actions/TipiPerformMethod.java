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
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    TipiValue dest = getParameter("destination");
    TipiValue bon = getParameter("breakOnError");
    boolean breakOnError = false;
    Operand brk = getEvaluatedParameter("breakOnError");

//    if (bon!=null) {
//      System.err.println("Found something");
      if (brk!=null) {
//
//        System.err.println("Found operand: "+brk.value);
        breakOnError = ((Boolean)brk.value).booleanValue();
      }
//      System.err.println("ALT: "+evaluate(bon.getValue()).value);

//      breakOnError =  bon.getValue().equals("true");
//    }
//
//    System.err.println("@@@@@@@@@@@@@ BreakOnError: "+breakOnError+" >> "+bon);
    String destination = (String) getParameter("destination").getValue();
    if (destination == null) {
      destination = "*";
    }
    TipiValue sourceTipi = getParameter("tipipath");
    Operand method = getEvaluatedParameter("method");
    TipiDataComponent evalTipi = null;
//    String evalMethod = null;
    try {
      evalTipi = (TipiDataComponent) evaluate(sourceTipi.getValue()).value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException("Can not evaluate tipi path expression. Switching to manual.");
//      TipiPathParser pp = new TipiPathParser(myComponent, myContext, sourceTipi.getValue());
//      evalTipi = pp.getTipi();
    }
//    try {
//      evalMethod = (String) evaluate(method.getValue()).value;
//    }
//    catch (Exception ex1) {
//      evalMethod = method.getValue();
//    }
    if (method == null) {
      throw new IllegalArgumentException("Error performing method. Method evaluated to null.");
    }
    if (sourceTipi == null || "".equals(sourceTipi)) {
      if (myComponent.getNearestNavajo() != null) {
        myContext.performTipiMethod(null, myComponent.getNearestNavajo(), destination, method.value.toString(),breakOnError);
      }
      else {
        myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(), destination, method.value.toString(),breakOnError);
      }
      return;
    }
    if (evalTipi == null) {
      if (myComponent.getNearestNavajo() != null) {
        Navajo n = myComponent.getNearestNavajo();
        System.err.println("Not a blank NAvajo!!!");
        myContext.performTipiMethod(null, n, destination, method.value.toString(),breakOnError);
      }
      else {
        System.err.println("Could not evaluate tipi. Calling service with blank navajo");
        myContext.performTipiMethod(null, NavajoFactory.getInstance().createNavajo(), destination, method.value.toString(),breakOnError);
      }
      return;
    }
    evalTipi.performService(myContext, destination, method.value.toString(),breakOnError);
  }
}
