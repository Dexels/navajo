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
    if(pp.getPathType() == pp.PATH_TO_TIPI){
      TipiComponent sourceComponent = pp.getComponent();
      if (sourceComponent != null) {
        try {
          //System.err.println("-------------------> Evaluating expression: " + expression);
          o = Expression.evaluate(expression, sourceComponent.getNearestNavajo());
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
      TipiComponent sourceComponent = pp.getComponent();
      Message m = pp.getMessage();
      if (sourceComponent != null && m != null) {
        try {
          //System.err.println("-------------------> Evaluating expression: " + expression);

          // Making a new Navajo because Otherwise it will start at the root, so for a ArrayMsg. it will allways evaluate the message at index 0 instraead of selected 'm'
          Navajo n = NavajoFactory.getInstance().createNavajo();
          Message bert = m.copy(n);
          n.addMessage(bert);
          o = Expression.evaluate(expression, n,null,bert, null);
          //System.err.println("\n\n Evaluating expression: " + expression);
          //System.err.println("Result : " + o.value);
          //System.err.println("\n Message was:");
          //n.write(System.err);
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