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
    if(sourceComponent != null){
      try {
        System.err.println("-------------------> Evaluating expression: " + expression);
        o = Expression.evaluate(expression, sourceComponent.getNearestNavajo());
        if(o.value.toString().equals("true")){
          valid = true;
        }
      }
      catch (TMLExpressionException ex) {
        ex.printStackTrace();
      }
      catch (SystemException ex) {
        ex.printStackTrace();
      }
    }else{
      System.err.println("ERROR: --------------------------> Could not find source tipi, ignoring condition");
      valid = true;
    }
    return valid;
  }

}