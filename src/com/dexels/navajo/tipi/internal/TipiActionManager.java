package com.dexels.navajo.tipi.internal;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * This is essentially a map of ActionFactories. All action definitions will be presented to this manager, and
 * it will create an ActionFactory for each different action type.
 */
public class TipiActionManager {
  private Map actionFactoryMap = new HashMap();
  public TipiActionManager() {
   }

  public void addAction(XMLElement actionDef, TipiContext context) throws TipiException {
    TipiActionFactory taf = new TipiActionFactory();
    taf.load(actionDef, context);
    actionFactoryMap.put(actionDef.getAttribute("name"), taf);
  }

  public TipiActionFactory getActionFactory(String name) throws TipiException {
    TipiActionFactory taf = (TipiActionFactory) actionFactoryMap.get(name);
    if (taf == null) {
      throw new TipiException("No action defined with name: " + name);
    }
    return taf;
  }

  public TipiAction instantiateAction(XMLElement instance, TipiEvent te, TipiComponent tc) throws TipiException {
    String name = (String) instance.getAttribute("type");
    TipiActionFactory taf = getActionFactory(name);
    return taf.instantateAction(instance, te, tc);
  }

  public ArrayList getDefinedActions() {
    ArrayList actions = new ArrayList();
    Iterator it = actionFactoryMap.keySet().iterator();
    while (it.hasNext()) {
      String name = (String) it.next();
      actions.add(name);
    }
    return actions;
  }
}