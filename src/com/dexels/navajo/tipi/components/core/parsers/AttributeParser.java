package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AttributeParser
    extends BaseTipiParser {
  public Object parse(TipiComponent source, String expression) {
    return getAttributeByPath(source, expression);
  }

  private Object getAttributeByPath(TipiComponent source, String path) {
    String componentPath = path.substring(0, path.indexOf(":"));
    String attr = path.substring(path.indexOf(":") + 1);
    TipiComponent tc = getTipiComponent(source, componentPath);
    return tc.getValue(attr);
  }
}