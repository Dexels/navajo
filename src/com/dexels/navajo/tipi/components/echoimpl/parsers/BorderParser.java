package com.dexels.navajo.tipi.components.echoimpl.parsers;

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
public class BorderParser
    extends TipiTypeParser {
  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    return parseBorder(expression);
  }

  private Object parseBorder(String s) {
//    StringTokenizer st = new StringTokenizer(s, "-");
//    String borderName = st.nextToken();
//    if ("etched".equals(borderName)) {
//      return BorderFactory.createEtchedBorder();
//    }
//    if ("raised".equals(borderName)) {
//      return BorderFactory.createRaisedBevelBorder();
//    }
//    if ("lowered".equals(borderName)) {
//      return BorderFactory.createLoweredBevelBorder();
//    }
//    if ("titled".equals(borderName)) {
//      String title = st.nextToken();
//      return BorderFactory.createTitledBorder(title);
//    }
//    if ("indent".equals(borderName)) {
//      try {
//        int top = Integer.parseInt(st.nextToken());
//        int left = Integer.parseInt(st.nextToken());
//        int bottom = Integer.parseInt(st.nextToken());
//        int right = Integer.parseInt(st.nextToken());
//        return BorderFactory.createEmptyBorder(top, left, bottom, right);
//      }
//      catch (Exception ex) {
//        System.err.println("Error while parsing border");
//      }
//    }
//    return BorderFactory.createEmptyBorder();
    return null;
  }
}
