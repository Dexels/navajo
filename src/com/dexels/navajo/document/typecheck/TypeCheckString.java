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

public class TypeCheckString extends TypeChecker {
  public TypeCheckString() {
  }
  public String getType() {
    return Property.STRING_PROPERTY;
  }

  /** @todo check number of invokations... Still seems to be calles too often */

  public String verify(Property p, String value) throws com.dexels.navajo.document.PropertyTypeException {
//    System.err.println("Entering string checker: "+value);
    if (value==null || "".equals(value)) {
      return value;
    }
    String cap = p.getSubType("capitalization");
    if (cap!=null) {
      if ("upper".equals(cap)) {
        System.err.println("Upper: "+value.toUpperCase());
        return value.toUpperCase();
      }
      if ("lower".equals(cap)) {
        System.err.println("Upper: "+value.toLowerCase());
        return value.toLowerCase();
      }
    }
    return value;
  }

}
