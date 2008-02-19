package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.*;

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
public class ColorParser
    extends TipiTypeParser {
  public Object parse(TipiComponent source, String expression,TipiEvent event) {
    return parseColor(expression);
  }

  private Color parseColor(String s) {
    if (!s.startsWith("#")) {
      throw new RuntimeException("BAD COLOR: " + s);
    }
    String st = s.substring(1);
    int in = Integer.parseInt(st, 16);
    return new Color(in);
  }
}
