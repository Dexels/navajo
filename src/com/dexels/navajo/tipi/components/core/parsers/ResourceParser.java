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
    URL u = myContext.getResourceURL(expression);
    if (u!=null) {
      return u;
    }
    if (myContext.isStudioMode()) {
      String project = System.getProperty("tipi.project.dir");
      if (project==null) {
        return null;
      }
      File projectDir = new File(project);
      File resourceDir = new File(projectDir,"resource");
      System.err.println("Looking for file: "+expression);
      File resFile = new File(resourceDir,expression);
      if (resFile.exists()) {
        URL url = null;
        try {
          url = resFile.toURL();
        }
        catch (MalformedURLException ex) {
          ex.printStackTrace();
          return null;
        }
        return url;
      } else {
        System.err.println("File not found: "+resFile.toString());
        return null;
      }
    } else {
      return null;
    }
  }
  public String toString(Object o, TipiComponent source) {
    return "Not possible";
  }
}
