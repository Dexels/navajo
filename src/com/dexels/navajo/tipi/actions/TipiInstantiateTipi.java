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

public class TipiInstantiateTipi
    extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException,
      com.dexels.navajo.tipi.TipiBreakException {
    instantiateTipi(false);
  }

  public static void instantiateByClass(TipiComponent parent, boolean force,
                                        String id, String className) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    System.err.println("PARENT NULL? "+(parent==null));
    t.instantiateTipi(true,parent, force, id, className, null, null);
  }

  public static void instantiateByDefinition(TipiComponent parent,
                                             boolean force, String id,
                                             String definitionName) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    t.instantiateTipi(false, parent, force, id, null, definitionName, null);
  }

  protected void instantiateTipiByDefinition(TipiComponent parent,
                                             boolean force, String id,
                                             String className,
                                             String definitionName) throws
      TipiException {
    instantiateTipi(false, parent, force, id, className, definitionName, null);
  }

  protected void instantiateTipiByClass(TipiComponent parent, boolean force,
                                        String id, String className,
                                        String definitionName) throws
      TipiException {
    instantiateTipi(true, parent, force, id, className, definitionName, null);
  }

  protected void instantiateTipi(boolean byClass, TipiComponent parent,
                                 boolean force, String id, String className,
                                 String definitionName, Map paramMap) throws
      TipiException {
    System.err.println("2: PARENT NULL? "+(parent==null));

    instantiateTipi(TipiContext.getInstance(), null, byClass, parent, force, id,
                    className, definitionName, null);
  }

  protected void instantiateTipi(TipiContext myContext,
                                 TipiComponent myComponent, boolean byClass,
                                 TipiComponent parent, boolean force, String id,
                                 String className, String definitionName,
                                 Map paramMap) throws TipiException {
    String componentPath;
    if (parent != null) {
      componentPath = parent.getPath() + "/" + id;
    }
    else {
      componentPath = "/" + id;
    }

    //System.err.println("ComponentPath: "+componentPath+" parentclass: "+parent.getClass());
    TipiPathParser tp = new TipiPathParser(myComponent, myContext,
                                           componentPath);
    TipiComponent comp = (TipiComponent) tp.getTipi();
    if (comp != null) {
      if (force) {
        myContext.disposeTipiComponent(comp);
      }
      else {
        comp.reUse();
        return;
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
    xe.setAttribute("id",id);

    if (paramMap!=null) {
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
    System.err.println("3: PARENT NULL? "+(parent==null));
    System.err.println("Adding component: "+inst.getId()+" to: "+parent.getPath());
    parent.addComponent(inst, myContext, null);
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

    instantiateTipi(TipiContext.getInstance(), myComponent, byClass, parent,
                    force, id, getParameter("class").getValue(),
                    getParameter("name").getValue(), parameterMap);

  }

}