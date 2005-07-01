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
public final class TipiSetValue extends TipiAction {
  
	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    
    String path = getParameter("to").getValue();
    String value = getParameter("from").getValue();
    //System.err.println("Evaluating setValue. From: "+value+" to: "+path);
    Operand evaluated = evaluate(path,event);
    Operand evaluatedValue = evaluate(value,event);
    //System.err.println("parsed to value: "+evaluatedValue.value);
    //System.err.println("parsed to type: "+evaluatedValue.type);

    if (evaluated == null) {
      //System.err.println(">>>>>>>>WARNING: NULL evaluation (to) in SETVALUE: path: " + path + " from: " + value + " in component: " + myComponent.getPath());
      return;
    }
    if (evaluated.value == null) {
      //System.err.println(">>>>>>>>WARNING: NULL value evaluation (to) in SETVALUE: path: " + path + " from: " + value + " in component: " + myComponent.getPath());
    }
    else {
      if (evaluated.value instanceof Property) {
          //System.err.println("Value (To) identified as property!");
        Property p = (Property) evaluated.value;
//        Operand evaluatedValue = evaluate(value,event);
        p.setAnyValue(evaluatedValue.value);
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
//          if (evaluatedValue.value!=null) {
//            p.setValue(evaluatedValue.value.toString());
//          } else {
//            p.setValue("");
//          }
//        }
      }
        //System.err.println("Evaluated from. Null? "+evaluatedValue==null);
      if (evaluatedValue!=null) {
        //System.err.println("Value: "+evaluatedValue.value+" type: "+evaluatedValue.type);
    }
      if (evaluated.value instanceof TipiReference) {
          //System.err.println("Value (To) identified as attributeref!");
          TipiReference p = (TipiReference) evaluated.value;
//            System.err.println("Attribute belongs to: "+p.getTipiComponent().getPath());
          p.setValue(evaluatedValue.value,myComponent);
      }
//      if (evaluated.value instanceof GlobalRef) {
//        GlobalRef p = (GlobalRef) evaluated.value;
//        p.setValue(value,myComponent);
//      }
//      if (evaluated.value instanceof SystemPropertyRef) {
//        SystemPropertyRef p = (SystemPropertyRef) evaluated.value;
//        p.setValue(value,myComponent);
//      }
    }
  }
}
