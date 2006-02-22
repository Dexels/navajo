package com.dexels.navajo.tipi.actions;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.Operand;

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
    instantiateTipi(false,event);
  }

  public static TipiComponent instantiateByClass(TipiComponent parent, boolean force,
                                                 String id, String className,Object constraints) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    // sort of hackish
    t.setContext(parent.getContext());
    TipiComponent tc = t.instantiateTipi(true, parent, force, id, className, null, null,constraints);
    tc.commitToUi();
    return tc;
  }

  public static TipiComponent instantiateNonTransientByClass(TipiComponent parent, boolean force,
                                                 String id, String className,Object constraints) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    // sort of hackish
    t.setContext(parent.getContext());
    TipiComponent ttt = t.instantiateTipi(true, parent, force, id, className, null, null,constraints);
    ttt.setTransient(false);
    return ttt;
  }

  public static TipiComponent instantiateByDefinition(TipiComponent parent,
      boolean force, String id,
      String definitionName, Object constraints) throws
      TipiException {
    TipiInstantiateTipi t = new TipiInstantiateTipi();
    // sort of hackish
    t.setContext(parent.getContext());
    return t.instantiateTipi(false, parent, force, id, null, definitionName, null,constraints);
  }

  protected TipiComponent instantiateTipiByDefinition(TipiComponent parent,
      boolean force, String id,
      String className,
      String definitionName, Object constraints) throws
      TipiException {
    return instantiateTipi(false, parent, force, id, className, definitionName, null, constraints);
  }

  protected TipiComponent instantiateTipiByClass(TipiComponent parent, boolean force,
                                                 String id, String className,
                                                 String definitionName, Object constraints) throws
      TipiException {
    return instantiateTipi(true, parent, force, id, className, definitionName, null,constraints);
  }

  protected TipiComponent instantiateTipi(boolean byClass, TipiComponent parent,
                                          boolean force, String id, String className,
                                          String definitionName, Map paramMap, Object constraints) throws
      TipiException {
    return instantiateTipi(myContext, null, byClass, parent, force, id,
                           className, definitionName, null,constraints);
  }

  protected TipiComponent instantiateTipi(TipiContext myContext,
                                          TipiComponent myComponent, boolean byClass,
                                          TipiComponent parent, boolean force, String id,
                                          String className, String definitionName,
                                          Map paramMap, Object constraints) throws TipiException {
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
//        System.err.println("Calling dispose from instantiate, with force= true");
//        System.err.println("Component path: "+comp.getPath());
        myContext.disposeTipiComponent(comp);
      }
      else {
        System.err.println("Instantiating, with force= false, so will just invoke onInstantiate");
        System.err.println("Component path: "+comp.getPath());
        comp.performTipiEvent("onInstantiate", null, false);
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
     parent.addComponent(inst, myContext, constraints);
    myContext.fireTipiStructureChanged(inst);
    return inst;
  }

  protected void instantiateTipi(boolean byClass, TipiEvent event) throws TipiException {
    String id = null;
    Object constraints = null;
    TipiValue forceVal = getParameter("force");
    String forceString = null;
    if (forceVal==null) {
        forceString = "false";    
    } else {
        forceString = "true";
    }
    TipiComponent parent = null;
    boolean force;
    if (forceString == null) {
      force = false;
    }
    else {
      force = forceString.equals("true");
    }
    try {
        constraints = getEvaluatedParameter("constraints", event);
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
                      null, parameterMap,constraints);
    }
    else {
      String definitionName = null;
      try {
        Operand ooo = getEvaluatedParameter("name", null);
        definitionName = (String)ooo.value;
      }
      catch (Exception ex1) {
        System.err.println("Trouble instantiating from definition. Actually, this probably means that you did not put quotes around the tipidefinition name,\nwhich is required by the new ISO-TIPI-2004 standard.");
        definitionName = getParameter("name").getValue();
      }
      instantiateTipi(myContext, myComponent, byClass, parent,
                      force, id, null,
                      definitionName, parameterMap,constraints);
    }
  }
}
