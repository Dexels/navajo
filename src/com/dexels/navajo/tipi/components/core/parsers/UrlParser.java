package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class UrlParser
    extends TipiTypeParser {
  public Object parse(TipiComponent source, String expression) {
//    System.err.println("Parsing url: "+expression);
    return getUrl(expression);
  }
  private URL getUrl(String path) {
    try {
//        int i = path.indexOf(":");
//        String urlPath = path.substring(i + 2);
        return new URL(path);
    }
    catch (MalformedURLException ex) {
      throw new IllegalArgumentException("supplied url not valid for: " + path);
    }
  }
}