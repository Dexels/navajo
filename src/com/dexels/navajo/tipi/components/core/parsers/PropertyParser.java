package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class PropertyParser
    extends BaseTipiParser {
  public Object parse(TipiComponent source, String expression) {
    return getPropertyValue(source, expression);
  }

  private Object getPropertyValue(TipiComponent source, String path) {
    Property p = getPropertyByPath(source, path);
    if (p != null) {
      return p.getTypedValue();
    }
    return null;
  }
}
