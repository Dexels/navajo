package com.dexels.navajo.document.types;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class NavajoType implements  Serializable {
  private Map<String,String> subTypeMap = null;

  public abstract boolean isEmpty();
  public NavajoType(String type) {
    String subtype = NavajoFactory.getInstance().getDefaultSubtypeForType(type);
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    }
  }

  public NavajoType() {
	  
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
      return subTypeMap.get(key);
    }
    return null;
  }

  public void setSubType(String subtype) {
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    }
  }
}
