package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.*;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiActionFactory  {
  protected String myName = null;
  protected Map myDefinedParams = new HashMap();
  protected Map myParams = new HashMap();
  protected TipiContext myContext = null;
  private Class myActionClass = null;
  public TipiActionFactory() {
  }
  public Object getActionParameter(String name) {
    /**@todo Implement this com.dexels.navajo.tipi.Executable method*/
    throw new java.lang.UnsupportedOperationException("Method getActionParameter() not yet implemented.");
  }

  public void load(XMLElement actionDef,TipiContext context) throws TipiException {
    if (actionDef==null || !actionDef.getName().equals("tipiaction")) {
      throw new IllegalArgumentException("Can not instantiate tipi action.");
    }
    String pack = (String) actionDef.getAttribute("package");
    myName = (String) actionDef.getAttribute("name");
    String clas = (String) actionDef.getAttribute("class");
    String fullDef = pack + "." + clas;
    context.setSplashInfo("Adding action: " + fullDef);
    System.err.println(">> "+fullDef);
    try {
      myActionClass = Class.forName(fullDef);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("Trouble loading action class: "+fullDef);
      throw new TipiException("Trouble loading action class: "+fullDef);
    }

    myContext = context;
    Vector children = actionDef.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement currentParam = (XMLElement)children.get(i);
      TipiValue tv = new TipiValue();
      tv.load(currentParam);
      myDefinedParams.put(tv.getName(),tv);
    }
  }

  public TipiAction instantateAction(XMLElement instance, TipiEvent te, TipiComponent tc) throws TipiException {
    // todo:
    // Instantiate the class.
    TipiAction newAction = null;
    try {
      newAction = (TipiAction) myActionClass.newInstance();
    }
    catch (IllegalAccessException ex) {
      throw new TipiException("Can not instantiate actionclass: "+newAction+" problem: "+ex.getMessage());
    }
    catch (InstantiationException ex) {
      throw new TipiException("Can not instantiate actionclass: "+newAction+" problem: "+ex.getMessage());
    }

    newAction.setContext(myContext);
    newAction.setComponent(tc);
    newAction.setEvent(te);

    // Check presence of supplied parameters in the defined parameters
    Vector c = instance.getChildren();
    for (int i = 0; i < c.size(); i++) {
      XMLElement x = (XMLElement)c.get(i);
      TipiValue instanceValue = new TipiValue(x);
      TipiValue defined = (TipiValue)myDefinedParams.get(x.getAttribute("name"));
      if (defined==null) {
        System.err.println("Parameter: "+x.getAttribute("name")+" unknown in action: "+myName);
      } else {
        defined.typeCheck(x.getAttribute("value"));
      }
      newAction.addParameter(instanceValue);
    }


    // Check presence of required parameters.
    Iterator it = myDefinedParams.values().iterator();
    while (it.hasNext()) {
      TipiValue current = (TipiValue)it.next();
      if (current.isRequired()) {
        if (!newAction.hasParameter(current.getName())) {
        throw new TipiException("Can not instantiate actionclass: "+newAction+" parameter: "+current.getName()+" missing!");
        }
        // Check for non required parameters not in the instance: Add them with the default value
      } else {
        if (!newAction.hasParameter(current.getName())) {
          newAction.addParameter((TipiValue)current.clone());
        }
      }
    }

    return newAction;
  }
}