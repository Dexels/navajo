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
//    String location = null;
    String forceString = getParameter("force").getValue();
    TipiComponent parent = null;
    boolean force;
    System.err.println("REQUESTED LOCATION: "+getParameter("location").getValue());

    if (forceString==null) {
      force = false;
    } else {
      force = forceString.equals("true");
    }

    try {
       id = (String) evaluate(getParameter("id").getValue()).value;
       Object o = evaluate((getParameter("location").getValue())).value;
       System.err.println("Location: " + o.toString());
       System.err.println("Class: " + o.getClass().toString());
       if(String.class.isInstance(o)){
         System.err.println("Location evaluated to a string, trying to get Tipi from that string (" + o.toString() + ")");
         o = evaluate("{"+o.toString()+"}").value;
       }
       parent = (TipiComponent)o;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      System.err.println("OOps: "+ex.getMessage());
    }
//    parent.addComponent();
    String componentPath = parent.getPath() + "/"+id;

    System.err.println("ComponentPath: "+componentPath+" parentclass: "+parent.getClass());
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
      if (!"location".equals(current)) {
        xe.setAttribute(current,evaluate( getParameter(current).getValue()).value);
//        System.err.println("Current param: "+current);
//        System.err.println("Value: "+getParameter(current).getValue());
//        System.err.println("Evaluated to: " + xe.getAttribute(current));
      }
      }
      catch (Exception ex1) {
        ex1.printStackTrace();
      }
    }
//    System.err.println("Instantiating: "+xe.toString());
    TipiComponent inst = myContext.instantiateComponent(xe);
    inst.setId(id);
    TipiPathParser pp = new TipiPathParser(myComponent, myContext, parent.getPath());
    TipiComponent dest = pp.getComponent();
    if (dest!=null) {
      dest.addComponent(inst,myContext,null);
    } else {
      System.err.println("Can not add component: "+getParameter("name")+" location not found: "+parent.getPath());
    }

 }

}