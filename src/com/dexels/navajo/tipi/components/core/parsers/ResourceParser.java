package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import java.net.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class ResourceParser
    extends BaseTipiParser {
  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    return myContext.getResourceURL(expression);
  }
  public String toString(Object o, TipiComponent source) {
    return "Not possible";
  }
}
