package com.dexels.navajo.tipi.actions;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiInstantiateTipi
    extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException,
      com.dexels.navajo.tipi.TipiBreakException {
    instantiateTipi(false);
  }

  public static TipiComponent instantiateByClass(TipiComponent parent, boolean force,
                                                 String id, String className) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    // sort of hackish
    t.setContext(parent.getContext());
    System.err.println("PARENT NULL? " + (parent == null));
    return t.instantiateTipi(true, parent, force, id, className, null, null);
  }

  public static TipiComponent instantiateByDefinition(TipiComponent parent,
      boolean force, String id,
      String definitionName) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    // sort of hackish
    t.setContext(parent.getContext());
    return t.instantiateTipi(false, parent, force, id, null, definitionName, null);
  }

  protected TipiComponent instantiateTipiByDefinition(TipiComponent parent,
      boolean force, String id,
      String className,
      String definitionName) throws
      TipiException {
    return instantiateTipi(false, parent, force, id, className, definitionName, null);
  }

  protected TipiComponent instantiateTipiByClass(TipiComponent parent, boolean force,
                                                 String id, String className,
                                                 String definitionName) throws
      TipiException {
    return instantiateTipi(true, parent, force, id, className, definitionName, null);
  }

  protected TipiComponent instantiateTipi(boolean byClass, TipiComponent parent,
                                          boolean force, String id, String className,
                                          String definitionName, Map paramMap) throws
      TipiException {
    System.err.println("2: PARENT NULL? " + (parent == null));
    return instantiateTipi(myContext, null, byClass, parent, force, id,
                           className, definitionName, null);
  }

  protected TipiComponent instantiateTipi(TipiContext myContext,
                                          TipiComponent myComponent, boolean byClass,
                                          TipiComponent parent, boolean force, String id,
                                          String className, String definitionName,
                                          Map paramMap) throws TipiException {
    String componentPath;
    if (parent != null) {
      componentPath = parent.getPath("component:/") + "/" + id;
    }
    else {
      componentPath = "component://" + id;
    }
    System.err.println("ComponentPath: " + componentPath + " parentclass: " + parent.getClass());
//    TipiPathParser tp = new TipiPathParser(myComponent, myContext,
//                                           componentPath);
//    TipiComponent comp = myContext.parse(myComponent,"tipi",componentPath);

//    TipiComponent comp = (TipiComponent) tp.getTipi();
    TipiComponent comp = (TipiComponent) (evaluate("{" + componentPath + "}").value);
    if (comp != null) {
      if (force) {
        myContext.disposeTipiComponent(comp);
      }
      else {
        comp.performTipiEvent("onInstantiate",null,true);
        comp.reUse();
        return comp;
      }
    }
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("component-instance");
    if (byClass) {
      xe.setAttribute("class", className);
    }
    else {
      xe.setAttribute("name", definitionName);
    }
    xe.setAttribute("id", id);
    if (paramMap != null) {
      Iterator it = paramMap.keySet().iterator();
      while (it.hasNext()) {
        try {
          String current = (String) it.next();
          if (!"location".equals(current)) {
            xe.setAttribute(current,
                            evaluate(getParameter(current).getValue()).value);
          }
        }
        catch (Exception ex1) {
          ex1.printStackTrace();
        }
      }
    }
//    System.err.println("Instantiating: "+xe.toString());
    TipiComponent inst = myContext.instantiateComponent(xe);
    inst.setId(id);
    parent.addComponent(inst, myContext, null);
    return inst;
  }

  protected void instantiateTipi(boolean byClass) throws TipiException {
    String id = null;
//    String location = null;
    String forceString = getParameter("force").getValue();
    TipiComponent parent = null;
    boolean force;
    System.err.println("REQUESTED LOCATION: " +
                       getParameter("location").getValue());
    if (forceString == null) {
      force = false;
    }
    else {
      force = forceString.equals("true");
    }
    try {
      id = (String) evaluate(getParameter("id").getValue()).value;
      Object o = evaluate( (getParameter("location").getValue())).value;
      //System.err.println("Location: " + o.toString());
      //System.err.println("Class: " + o.getClass().toString());
      if (String.class.isInstance(o)) {
        System.err.println(
            "Location evaluated to a string, trying to get Tipi from that string (" +
            o.toString() + ")");
        o = evaluate("{" + o.toString() + "}").value;
      }
      parent = (TipiComponent) o;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      System.err.println("OOps: " + ex.getMessage());
    }
//    parent.addComponent();
    if (byClass) {
      instantiateTipi(myContext, myComponent, byClass, parent,
                      force, id, getParameter("class").getValue(),
                      null, parameterMap);
    }
    else {
      instantiateTipi(myContext, myComponent, byClass, parent,
                      force, id, null,
                      getParameter("name").getValue(), parameterMap);
    }
  }
}
