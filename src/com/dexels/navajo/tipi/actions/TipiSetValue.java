package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.internal.*;

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
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    String path = getParameter("to").getValue();
    String value = getParameter("from").getValue();
    Operand evaluated = evaluate(path, event);
    Operand evaluatedValue = evaluate(value, event);
    if (evaluated == null) {
      System.err.println(">>>>>>>>WARNING: NULL evaluation (to) in SETVALUE: path: " + path + " from: " + value + " in component: " + myComponent.getPath());
      return;
    }
    if (evaluated.value == null) {
      System.err.println(">>>>>>>>WARNING: NULL value evaluation (to) in SETVALUE: path: " + path + " from: " + value + " in component: " + myComponent.getPath());
    }
    else {
      if (evaluated.value instanceof Property) {
        throw new UnsupportedOperationException("Something is seriously wrong in Tipi. Consult Frank");
//        Property p = (Property) evaluated.value;
//        if (evaluated.type.equals(Property.FLOAT_PROPERTY)) {
//          p.setValue( (Double) evaluatedValue.value);
//        }
//        else if (evaluated.type.equals(Property.INTEGER_PROPERTY)) {
//          p.setValue( (Integer) evaluatedValue.value);
//        }
//        else if (evaluated.type.equals(Property.DATE_PROPERTY)) {
//          p.setValue( (java.util.Date) evaluatedValue.value);
//        }
//        else if (evaluated.type.equals(Property.BOOLEAN_PROPERTY)) {
//          p.setValue( (Double) evaluatedValue.value);
//        }
//        else {
//          if (evaluatedValue.value != null) {
//            p.setValue(evaluatedValue.value.toString());
//          }
//          else {
//            p.setValue("");
//          }
//        }
      }


// STRANGE WHY ISN'T THE evaluatedValue passed?
//      if (evaluated.value instanceof AttributeRef) {
//        AttributeRef p = (AttributeRef) evaluated.value;
//        p.setValue(value, myComponent);
//      }


      // When the classdefs are fixed, this check is redundant and can be removed
      if (!(evaluated.value instanceof TipiReference)) {
        System.err.println("To field in setvalue is of wrong type");
        return;
      }
      TipiReference tr = (TipiReference)evaluated.value;
      tr.setValue(value,evaluatedValue,myComponent);

//      if (evaluated.value instanceof PropertyRef) {
//        PropertyRef p = (PropertyRef) evaluated.value;
//        p.setValue(value,evaluatedValue, myComponent);
//      }
//
//
//      if (evaluated.value instanceof AttributeRef) {
//        AttributeRef p = (AttributeRef) evaluated.value;
//        p.setValue(value,evaluatedValue, myComponent);
//      }
//      if (evaluated.value instanceof GlobalRef) {
//        GlobalRef p = (GlobalRef) evaluated.value;
//        p.setValue(value,evaluatedValue, myComponent);
//      }
//      if (evaluated.value instanceof SystemPropertyRef) {
//        SystemPropertyRef p = (SystemPropertyRef) evaluated.value;
//        p.setValue(value,evaluatedValue, myComponent);
//      }
    }
  }
}
