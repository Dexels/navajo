package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSetValue
    extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {


    String path = getParameter("to").getValue();
    String value = getParameter("from").getValue();
    Operand evaluated = evaluate(path);

//    System.err.println("from: "+value);
//    System.err.println("to: "+path);
    if (evaluated == null) {
      System.err.println(">>>>>>>>WARNING: NULL evaluation in SETVALUE: path: " + path + " from: " + value + " in component: " + myComponent.getPath());
      return;
    }

    //System.err.println("Evaluated value: " + evaluatedValue.value);
    if (evaluated.value == null) {
      System.err.println(">>>>>>>>WARNING: NULL value evaluation in SETVALUE: path: " + path + " from: " + value + " in component: " + myComponent.getPath());
    }
    else {
      if (evaluated.value instanceof Property) {
        Property p = (Property) evaluated.value;
        Operand evaluatedValue = evaluate(value);
        if (evaluated.type.equals(Property.FLOAT_PROPERTY)) {
          p.setValue( (Double) evaluatedValue.value);
        }
        else if (evaluated.type.equals(Property.INTEGER_PROPERTY)) {
          p.setValue( (Integer) evaluatedValue.value);
        }
        else if (evaluated.type.equals(Property.DATE_PROPERTY)) {
          p.setValue( (java.util.Date) evaluatedValue.value);
        }
        else if (evaluated.type.equals(Property.BOOLEAN_PROPERTY)) {
          p.setValue( (Double) evaluatedValue.value);
        }
        else {
          p.setValue( evaluatedValue.value.toString());
//        System.err.println("SET TO VALUE (property): "+p.getValue());
        }
      }
      if (evaluated.value instanceof AttributeRef) {
        AttributeRef p = (AttributeRef) evaluated.value;
        p.setValue(value, myComponent);
      }
    }
  }
}