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

  public boolean hasParameter(String name) {
    return parameterMap.containsKey(name);
  }
  public TipiValue getParameter(String name) {
    return (TipiValue)parameterMap.get(name);
  }

  protected Operand evaluate(String expr) {
    Operand o = null;
    System.err.println("About to evaluate: "+expr);
    System.err.println("Navajo: ");
    if (myComponent.getNearestNavajo()!=null) {
      try {
        myComponent.getNearestNavajo().write(System.out);
      }
      catch (NavajoException ex1) {
        ex1.printStackTrace();
      }
    } else {
      System.err.println("Null navajo...");
    }

    try {
      myContext.setCurrentComponent(myComponent);
      o = Expression.evaluate(expr, myComponent.getNearestNavajo(), null, null, null, myContext);
    }
    catch (Exception ex) {
      System.err.println("Not happy while evaluating expression: "+expr+" message: "+ex.getMessage());
      Operand op = new Operand(expr,Property.STRING_PROPERTY,"");
      return o;
    } catch (Error ex) {
      System.err.println("Not happy while evaluating expression: "+expr+" message: "+ex.getMessage());
     Operand op = new Operand(expr,Property.STRING_PROPERTY,"");
     return o;
    }
    System.err.println("About to examine operand: "+o.type);
    System.err.println("Reported value: "+o.value);
    if (o.type.equals(Property.STRING_PROPERTY)) {
      if (o.value!=null ) {
        String s = (String)o.value;
        if (s.length()>1) {
          if (s.charAt(0)=='\'' && s.charAt(s.length()-1)=='\'') {
            o.value = s.substring(1,s.length()-2);
            System.err.println(">>>>> "+o.value);
          }
        }
      }
    }
    return o;
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