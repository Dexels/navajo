package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.parser.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiAction {

  protected TipiContext myContext;
  protected TipiActionFactory myActionFactory;
  protected TipiEvent myEvent;
  protected TipiComponent myComponent;

  protected Map parameterMap = new HashMap();
  protected TipiCondition myCondition;

  public void init(TipiComponent tc, TipiEvent te, TipiActionFactory tf, TipiContext context) {
    myContext = context;
    myActionFactory = tf;
    myComponent = tc;
    myEvent = te;
  }

  public void addParameter(TipiValue tv) {
    parameterMap.put(tv.getName(),tv);
  }

  public XMLElement store() {
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("action");
    /** @todo IMPLEMENT */
    return xe;
  }

  public boolean hasParameter(String name) {
    return parameterMap.containsKey(name);
  }
  public TipiValue getParameter(String name) {
     return (TipiValue)parameterMap.get(name);
  }

  protected Operand evaluate(String expr) {
    return myContext.evaluate(expr,myComponent);
  }

  public Operand getEvaluatedParameter(String name) {
    TipiValue t = getParameter(name);
    if (t==null) {
      return null;
    }
    return evaluate(t.getValue());
  }
  public boolean checkCondition() throws TipiException, TipiBreakException{
    if (myCondition==null) {
      return true;
    }
   return myCondition.evaluate(myComponent.getNearestNavajo(), myContext, myComponent, myEvent);
  }

  public void executeAction() throws TipiBreakException,TipiException {
    if (checkCondition()) {
      execute();
    }

  }

  protected abstract void execute() throws TipiBreakException,TipiException;

    public TipiCondition getCondition() {
      return myCondition;
    }

    public void setCondition(TipiCondition tc) {
      myCondition = tc;
    }

    public void setContext(TipiContext tc) {
      myContext = tc;
    }

    public void setComponent(TipiComponent tc) {
      myComponent = tc;
    }

    public void setEvent(TipiEvent te) {
      myEvent = te;
    }
}