package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AttributeRefParser
    extends BaseTipiParser {
  public Object parse(TipiComponent source, String expression) {
    return getAttributeRefByPath(source, expression);
  }

  private AttributeRef getAttributeRefByPath(TipiComponent source, String path) {
    System.err.println("PARSING ATTRIBUTEREF: "+path);
    String componentPath = path.substring(0, path.indexOf(":"));
    String attr = path.substring(path.indexOf(":") + 1);
    TipiComponent tc = getTipiComponent(source, componentPath);
    return tc.getAttributeRef(attr);
  }

  public String toString(Object o, TipiComponent source) {
    AttributeRef ar = (AttributeRef)o;
    TipiComponent tc = ar.getTipiComponent();
    return tc.getPath()+":"+ar.getName();
  }

}
