package com.dexels.navajo.tipi.internal;

import java.util.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiAction
    implements TipiExecutable {
  protected TipiContext myContext;
  protected TipiActionFactory myActionFactory;
//  protected TipiEvent myEvent;
  protected TipiComponent myComponent;
  protected String myType;
  protected Map parameterMap = new HashMap();
//  protected TipiCondition myCondition;
  protected abstract void execute(TipiEvent event) throws TipiBreakException, TipiException;

//  protected TipiActionBlock myActionBlock;
  public void init(TipiComponent tc, TipiEvent te, TipiActionFactory tf, TipiContext context) {
    myContext = context;
    myActionFactory = tf;
    myComponent = tc;
//    myEvent = te;
  }

  public void addParameter(TipiValue tv) {
    parameterMap.put(tv.getName(), tv);
  }

  public void performAction(TipiEvent te) throws TipiBreakException, TipiException {
    if (myComponent.isDisposed()) {
      System.err.println("\n**** BREAKING. COMPONENT DISPOSED: "+myComponent.getId());
      throw new TipiBreakException();
    }
    try {
      myContext.performedAction(myComponent, this);
    }
    catch (BlockActivityException ex1) {
      System.err.println("Blocked exception");
      return;
    }
    execute(te);
  }

  public XMLElement store() {
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("action");
    xe.setAttribute("type", getType());
    Iterator it = parameterMap.keySet().iterator();
    while (it.hasNext()) {
      XMLElement pr = new CaseSensitiveXMLElement();
      pr.setName("param");
      String name = (String) it.next();
      TipiValue value = (TipiValue) parameterMap.get(name);
      pr.setAttribute("name", name);
      pr.setAttribute("value", value.getValue());
      xe.addChild(pr);
    }
    return xe;
  }

  public void setType(String type) {
    myType = type;
  }

  public String getType() {
    return myType;
  }

  public boolean hasParameter(String name) {
    return parameterMap.containsKey(name);
  }

  public TipiValue getParameter(String name) {
    return (TipiValue) parameterMap.get(name);
  }

  public ArrayList getParams() {
    ArrayList parms = new ArrayList(parameterMap.values());
    return parms;
  }

  public Operand evaluate(String expr,TipiEvent event) {
    return myContext.evaluate(expr, myComponent, event);
  }

  public Operand getEvaluatedParameter(String name,TipiEvent event) {
    TipiValue t = getParameter(name);
    if (t == null) {
      return null;
    }
    return evaluate(t.getValue(),event);
  }

  public void setContext(TipiContext tc) {
    myContext = tc;
  }

  public void setComponent(TipiComponent tc) {
    myComponent = tc;
  }

  public TipiComponent getComponent() {
    return myComponent;
  }
//
//  public void setEvent(TipiEvent te) {
//    myEvent = te;
//  }

  public int getExecutableChildCount() {
    return 0;
  }

  public TipiExecutable getExecutableChild(int index) {
    return null;
  }
}
