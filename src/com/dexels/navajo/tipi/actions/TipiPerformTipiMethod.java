package com.dexels.navajo.tipi.actions;

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
public class TipiPerformTipiMethod
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    TipiComponent t = null;
    String name = null;
    String path = null;
    try {
      path = getParameter("path").getValue();
//      System.err.println("Evaluating : "+path);
      if (myComponent!=null) {
//        System.err.println("Not null, path: "+myComponent.getPath());
      }
      t = (TipiComponent) myContext.evaluate(path, myComponent, event).value;
//      t = (TipiComponent) myComponent.evaluateExpression(path);
      name = (String) evaluate(getParameter("name").getValue(),event).value;
//    TipiComponent tc = getTipiComponentByPath(source,context,path);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
//    TipiComponent tc  = new TipiPathParser(myComponent, myContext, path).getComponent();
    if (t != null) {
      t.performMethod(name, this,event);
    }
    else {
      throw new TipiException("performTipiMethod: Can not locate tipicomponent name: " + path +" method: "+name);
    }
  }

//  public void performMethod(TipiComponent tc, String methodName) throws TipiException {
//    TipiComponentMethod tcm = tc.getTipiComponentMethod(methodName);
//    if (tcm == null) {
//      throw new TipiException("Could not find component method: " + methodName);
//    }
//    tc.performMethod(methodName, this,null);
//  }
}
