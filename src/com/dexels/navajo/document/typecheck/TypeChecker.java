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
    return loadSubTypes(p.getSubType());
  }

  private Map loadSubTypes(String subType){
    Map m = new HashMap();

    if (subType==null|| "".equals(subType)) {
      return m;
    }
    StringTokenizer st = new StringTokenizer(subType,",");
    while (st.hasMoreTokens()) {
      String next = st.nextToken();
      int i = next.indexOf("=");
      String key = next.substring(0,i);
      String value = next.substring(i+1,next.length());
      m.put(key,value);
    }
    return m;

  }

  public String getSubType(String subType, String key){
    Map m = loadSubTypes(subType);
    if(m != null){
      return (String)m.get(key);
    }
    return null;
  }
}
