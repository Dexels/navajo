package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiInstantiateTipi extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    instantiateTipi(false);
  }

  protected void instantiateTipi(boolean byClass) throws TipiException {
    String id = null;
    String location = null;
    String forceString = getParameter("force").getValue();
    boolean force;

    if (forceString==null) {
      force = false;
    } else {
      force = forceString.equals("true");
    }

    try {
       id = (String) evaluate(getParameter("id").getValue()).value;
       location = (String)evaluate((getParameter("location").getValue())).value;
    }
    catch (Exception ex) {
      System.err.println("OOps: "+ex.getMessage());
    }
    String componentPath = location + "/"+id;

    TipiPathParser tp = new TipiPathParser(myComponent,myContext,componentPath);
    TipiComponent comp =  (TipiComponent)tp.getTipi();
    if (comp!=null) {
      if (force) {
        myContext.disposeTipiComponent(comp);
      } else {
        comp.reUse();
        return;
      }
    }
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("component-instance");
    if (byClass) {
      xe.setAttribute("class",getParameter("class").getValue());
    } else {
      xe.setAttribute("name",getParameter("name").getValue());
    }

    Iterator it = parameterMap.keySet().iterator();
    while (it.hasNext()) {
      try {
      String current = (String)it.next();
      xe.setAttribute(current,evaluate( getParameter(current).getValue()).value);
      System.err.println("Current param: "+current);
      System.err.println("Value: "+getParameter(current).getValue());
        System.err.println("Evaluated to: " + xe.getAttribute(current));
      }
      catch (Exception ex1) {
        ex1.printStackTrace();
      }
    }
//    System.err.println("Instantiating: "+xe.toString());
    TipiComponent inst = myContext.instantiateComponent(xe);
    inst.setId(id);
    TipiPathParser pp = new TipiPathParser(myComponent, myContext, location);
    TipiComponent dest = pp.getComponent();
    if (dest!=null) {
      dest.addComponent(inst,myContext,null);
    } else {
      System.err.println("Can not add component: "+getParameter("name")+" location not found: "+location);
    }

 }

}