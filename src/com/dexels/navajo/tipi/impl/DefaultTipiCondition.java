package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.Operand;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiCondition extends TipiCondition{
  public DefaultTipiCondition() {
  }

  public boolean evaluate(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException, TipiException {
    return evaluateCondition(context, source);
  }

  private boolean evaluateCondition(TipiContext context, Object source) throws TipiException{
    //from_path
    boolean valid = false;
    String from_path = (String)myParams.get("tipipath");
    String expression = (String)myParams.get("expression");
    Operand o;
    TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, from_path);
    TipiComponent sourceComponent = pp.getComponent();
    context.setCurrentComponent((TipiComponent)source);
    if(pp.getPathType() == pp.PATH_TO_TIPI){
      if (sourceComponent != null) {
        try {
          //System.err.println("-------------------> Evaluating expression: " + expression);
          o = Expression.evaluate(expression, sourceComponent.getNearestNavajo(), null, null, null, context);
          if (o.value.toString().equals("true")) {
            valid = true;
          }
        }
        catch (TMLExpressionException ex) {
          ex.printStackTrace();
        }
        catch (SystemException ex) {
          ex.printStackTrace();
        }
      }
      else {
        System.err.println("ERROR: --------------------------> Could not find source tipi, returning FALSE");
        valid = false;
      }
    }else if(pp.getPathType() == pp.PATH_TO_MESSAGE){
      Message m = pp.getMessage();
      if (sourceComponent != null && m != null) {
        try {
          // Use a copy of the Message. ArrayMessages remain a little tricky
          Navajo n = NavajoFactory.getInstance().createNavajo();
          Message bert = m.copy(n);
          n.addMessage(bert);
          o = Expression.evaluate(expression, n,null,bert, null, context);
          if (o.value.toString().equals("true")) {
            valid = true;
          }
        }
        catch (TMLExpressionException ex) {
          ex.printStackTrace();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      else {
        System.err.println("ERROR: --------------------------> Could not find source tipi for MESSAGE, ignoring condition");
        valid = true;
      }
    }else{
      throw new TipiException("Cannot put a condition on a component or property source");
    }
    return valid;
  }

}