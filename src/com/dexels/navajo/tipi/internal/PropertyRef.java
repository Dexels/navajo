package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyRef implements TipiReference {
  private final Property myProperty;
  public PropertyRef(Property p) {
    myProperty = p;
  }

  public void setValue(Object expression, TipiComponent tc) {
//    if (evaluated.value instanceof Property) {
//      Property p = (Property) evaluated.value;
//    System.err.println("Setvalue called with: "+expression+" operand: "+expression);
    // SORT OF BIG CHANGE NOW USES ONLY THE EXPRESSION (WHICH IS EVALUATED ALREADY)
    // evaluatedVAlue is ignored!!!
    
    
//    if (myProperty==null) {
//      System.err.println("Warning. Reference to null property. Ignoring");
//      return;
//    }
//      if (myProperty.getType().equals(Property.FLOAT_PROPERTY)) {
//        myProperty.setValue( (Double) evaluatedValue.value);
//      }
//      else if (myProperty.getType().equals(Property.INTEGER_PROPERTY)) {
//        myProperty.setValue( (Integer) evaluatedValue.value);
//      }
//      else if (myProperty.getType().equals(Property.DATE_PROPERTY)) {
//        myProperty.setValue( (java.util.Date) evaluatedValue.value);
//      }
//      else if (myProperty.getType().equals(Property.BOOLEAN_PROPERTY)) {
//        myProperty.setValue( (Double) evaluatedValue.value);
//      }
//      else {
//        if (evaluatedValue.value != null) {
//          myProperty.setValue(evaluatedValue.value.toString());
//        }
//        else {
//          myProperty.setValue("");
//        }
//      }
//    }


  }

}
