package com.dexels.navajo.tipi.components.echoimpl.parsers;

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
public class ColorParser
    extends TipiTypeParser {
  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    System.err.println("Hatsa...");
    return parseColor(expression);
  }

  private Color parseColor(String s) {
    System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><><><><OUWE>> PWPPPPWOOOAAARSSE COLLER: " + s);
    if (!s.startsWith("#")) {
      System.err.println("Eating runtime..don't snap this color: " + s);
      System.exit(0);
      throw new RuntimeException("BAD COLOR: " + s);
    }
    String st = s.substring(1);
    System.err.println("Before parsing the color.." + st);
    int in = Integer.parseInt(st, 16);
    System.err.println("Errrrrr.r.r.r.r.r.r:  " + in);
    return new Color(in);
  }
}
