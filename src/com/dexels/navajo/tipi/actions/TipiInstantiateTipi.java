package com.dexels.navajo.tipi.actions;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.parser.*;

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
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException,
      com.dexels.navajo.tipi.TipiBreakException {
    instantiateTipi(false);
  }

  public static TipiComponent instantiateByClass(TipiComponent parent, boolean force,
                                                 String id, String className) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    // sort of hackish
    t.setContext(parent.getContext());
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
    /** @todo Should we allow null events? */
    Operand op = evaluate("{" + componentPath + "}",null);

    TipiComponent comp = null;
    if (op!=null) {
      comp = (TipiComponent)op.value;
    }
    if (comp != null) {
      if (force) {
        myContext.disposeTipiComponent(comp);
      }
      else {
        comp.performTipiEvent("onInstantiate", null, true);
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
                            evaluate(getParameter(current).getValue(),null).value);
          }
        }
        catch (Exception ex1) {
          ex1.printStackTrace();
        }
      }
    }
    TipiComponent inst = myContext.instantiateComponent(xe);
    inst.setId(id);
    parent.addComponent(inst, myContext, null);
    myContext.fireTipiStructureChanged(inst);
    return inst;
  }

  protected void instantiateTipi(boolean byClass) throws TipiException {
    String id = null;
    String forceString = getParameter("force").getValue();
    TipiComponent parent = null;
    boolean force;
    if (forceString == null) {
      force = false;
    }
    else {
      force = forceString.equals("true");
    }
    try {
      id = (String) evaluate(getParameter("id").getValue(),null).value;
      Object o = evaluate( (getParameter("location").getValue()),null).value;
      if (String.class.isInstance(o)) {
        System.err.println(
            "Location evaluated to a string, trying to get Tipi from that string (" +
            o.toString() + ")");
        o = evaluate("{" + o.toString() + "}",null).value;
      }
      parent = (TipiComponent) o;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      System.err.println("OOps: " + ex.getMessage());
    }
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
