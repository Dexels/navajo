package com.dexels.navajo.tipi.components.echoimpl.parsers;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class FontParser
    extends TipiTypeParser {
  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    return parseFont(expression);
  }

  private Font parseFont(String s) {
    StringTokenizer str = new StringTokenizer(s, "-");
    String name = str.nextToken();
    int size = Integer.parseInt(str.nextToken());
    int style = Integer.parseInt(str.nextToken());
    // try logical:
    Font f = new Font(name, style, size);
    if (f != null) {
      return f;
    }
    return null;
  }

}
