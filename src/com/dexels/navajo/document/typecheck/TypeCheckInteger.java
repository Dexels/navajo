package com.dexels.navajo.document.typecheck;

import com.dexels.navajo.document.*;
import java.util.*;

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
  public String verify(Property p, String value) throws com.dexels.navajo.document.PropertyTypeException {
    if (value==null || "".equals(value)) {
      return value;
    }
    try {
  int v = Integer.parseInt(value);

    Map m = loadSubtypes(p);
    String max = (String)m.get("max");
    if (max!=null) {
      int mx = Integer.parseInt(max);
      if (v>mx) {
//        p.setValue((String)null);
        throw new PropertyTypeException(p,"Integer larger than maximum. ("+mx+">"+v+")");
      }
    }
    String min = (String)m.get("min");
    if (min!=null) {
      int mn = Integer.parseInt(min);
      if (v<mn) {
//        p.setValue((String)null);
        throw new PropertyTypeException(p,"Integer smaller than minimum. ("+v+"<"+mn+")");
      }
    }
    }
    catch (NumberFormatException ex) {
      // Only throw type exceptions when subtypes are defined.
      // This is to prevent breaking old code.
      if (p.getSubType()!=null) {
        throw new PropertyTypeException(ex,p,"Not a valid integer!");
      } else {
        System.err.println("Warning. Ignoring invalid integer: "+value+" for property: "+p.getName());
      }
    }

    return value;
  }

}
