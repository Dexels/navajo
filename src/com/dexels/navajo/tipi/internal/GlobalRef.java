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

public class GlobalRef  implements TipiReference{
  private final String name;
  private final TipiContext myContext;

  public GlobalRef(String name,TipiContext context) {
    this.name = name;
    myContext = context;
  }

  public void setValue(Object expression, TipiComponent tc) {
    myContext.setGlobalValue(name,expression);
  }
}
