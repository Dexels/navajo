package com.dexels.navajo.document.typecheck;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TypeCheckInteger extends TypeChecker {
  public TypeCheckInteger() {
  }
  public String getType() {
    return Property.INTEGER_PROPERTY;
  }
  public void verify(Property p, String value) throws com.dexels.navajo.document.PropertyTypeException {
    if (value==null || "".equals(value)) {
      return;
    }
    try {
      System.err.println("Entering typechecker: "+p.getValue()+" type: "+p.getType()+" new value: "+value+" subtype: "+p.getSubType());
      int v = Integer.parseInt(value);
      if (p.getSubType()!=null) {
        if (Property.SUBTYPE_POSITIVE.equals(p.getSubType())) {
          if (v<0) {
            p.setValue((String)null);
            throw new PropertyTypeException(p,"Not a positive integer.");
          }
        }
        if (Property.SUBTYPE_NEGATIVE.equals(p.getSubType())) {
          if (v>=0) {
            p.setValue((String)null);
            throw new PropertyTypeException(p,"Not a negative integer.");
          }
        }
      }
    }
    catch (NumberFormatException ex) {
      if (p.getSubType()!=null) {
        p.setValue((String)null);
        throw new PropertyTypeException(ex,p,"Not a valid integer!");
      } else {
        System.err.println("Warning. Ignoring invalid integer: "+value+" for property: "+p.getName());
      }
    }

  }

}
