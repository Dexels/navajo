package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.Property;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class GetPropertyType extends FunctionInterface {
  public GetPropertyType() {
  }
  public String remarks() {
    return "Gets the type of property as a string";
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    if (getOperands().size() != 1) {
      throw new TMLExpressionException(this, "Invalid function call");
    }
   Object o = getOperand(0);
   if (!(o instanceof String)) {
     throw new TMLExpressionException(this, "String argument expected");
   }
   String propertyName = (String) o;
   Property p = currentMessage.getProperty(propertyName);
   if (p == null) {
     throw new TMLExpressionException(this, "Property " + propertyName + " not found");
   }
   return p.getType();
  }
  public String usage() {
   return "GetPropertyType(property name)";
  }

}