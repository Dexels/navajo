package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GlobalRef {
  private final String name;
  private final TipiContext myContext;

  public GlobalRef(String name,TipiContext context) {
    this.name = name;
    myContext = context;
  }

  public void setValue(Object value) {
    myContext.setGlobalValue(name,value);
  }
}
