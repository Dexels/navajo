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
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    TipiComponent t = (TipiComponent) evaluate(getParameter("path").getValue()).value;
    String name = (String) evaluate(getParameter("name").getValue()).value;
//    TipiComponent tc = getTipiComponentByPath(source,context,path);
//    TipiComponent tc  = new TipiPathParser(myComponent, myContext, path).getComponent();
    if (t != null) {
      t.performMethod(name, this);
    }
    else {
      throw new TipiException("performTipiMethod: Can not locate tipicomponent name: " + name);
    }
  }

  public void performMethod(TipiComponent tc, String methodName) throws TipiException {
//    XMLElement invocation = (XMLElement)componentMethods.get(methodName);
//    if (invocation == null) {
//      throw new RuntimeException("No such method in tipi!");
//    }
//    if (!invocation.getName().equals("action")) {
//      throw new IllegalArgumentException("I always thought that a TipiComponent method would be called with an invocation called action, and not: " + invocation.getName());
//    }
//    if (!"performTipiMethod".equals(invocation.getStringAttribute("type"))) {
//      throw new IllegalArgumentException("I always thought that a TipiComponent method would be called with an action invocation with type: performTipiMethod, and not: " + invocation.getStringAttribute("type"));
//    }
    TipiComponentMethod tcm = tc.getTipiComponentMethod(methodName);
    if (tcm == null) {
      throw new TipiException("Could not find component method: " + methodName);
    }
//    if (tcm.checkFormat(methodName, invocation)) {
//      tcm.loadInstance(invocation);
    tc.performMethod(methodName, this);
//      tc.performComponentMethod(methodName,  tcm);
//    }
  }
}