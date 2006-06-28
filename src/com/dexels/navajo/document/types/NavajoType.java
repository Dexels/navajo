package com.dexels.navajo.document.types;

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

public abstract class NavajoType implements Comparable {
  private Map subTypeMap = null;

  public abstract boolean isEmpty();
  public NavajoType(String type) {
    String subtype = NavajoFactory.getInstance().getDefaultSubtypeForType(type);
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    }
  }

  public NavajoType(String type, String subtype) {
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    } else {
      subtype = NavajoFactory.getInstance().getDefaultSubtypeForType(type);
      if (subtype!=null) {
        subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
      }

    }
  }

  public String getSubType(String key) {
    if (subTypeMap!=null) {
      return (String)subTypeMap.get(key);
    }
    return null;
  }

  public void setSubType(String subtype) {
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    }
  }
}
