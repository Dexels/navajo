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

public abstract class TypeChecker {
  public abstract String verify(Property p, String value) throws PropertyTypeException;
  public abstract String getType();

  protected Map loadSubtypes(Property p) {
    Map m = new HashMap();
    String s = p.getSubType();

    if (s==null|| "".equals(s)) {
      return m;
    }
    System.err.println("Subtype...: "+p.getSubType());
    StringTokenizer st = new StringTokenizer(s,",");
    while (st.hasMoreTokens()) {
      String next = st.nextToken();
//      System.err.println("Token: "+next);
      int i = next.indexOf("=");
      String key = next.substring(0,i);
      String value = next.substring(i+1,next.length());
//      System.err.println("key: "+key);
//      System.err.println("value: "+value);
      m.put(key,value);
    }
    return m;
  }
}
