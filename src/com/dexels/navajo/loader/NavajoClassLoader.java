package com.dexels.navajo.loader;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * $Id$
 *
 */

import org.dexels.utils.*;
import java.io.*;

/**
 * This class implements the Navajo Class Loader. It is used to implement re-loadable and hot-loadable Navajo adapter classes
 * and Navajo Expression Language functions.
 * It uses a cache for fast multiple instantiation of loaded Classes.
 */

class JarFilter implements FilenameFilter {
  public boolean accept(File f, String name) {
    if (name.endsWith("jar"))
      return true;
    else
      return false;
  }
}

public class NavajoClassLoader extends MultiClassLoader {

  private String adapterPath = "";

  public NavajoClassLoader(String adapterPath) {
    System.out.println("Initializing NavajoClassLoader: adapterPath = " + adapterPath);
    this.adapterPath = adapterPath;
  }

  /**
   * Use clearCache() to clear the Class cache, allowing a re-load of new jar files.
   */
  public void clearCache() {
    super.clearCache();
  }

  /**
   * Always use this method to load a class. It uses the cache first before retrieving the class from a jar resource.
   */
  public Class getClass(String className) throws ClassNotFoundException {
    Class c = (Class) classes.get(className);
    if (c == null) {
      return Class.forName(className, false, this);
    }
    else {
      System.out.println("Found class in cache");
      return c;
    }
  }

  protected byte[] loadClassBytes (String className)
  {
    // Support the MultiClassLoader's class name munging facility.
    className = formatClassName (className);

    File f = new File(adapterPath);
    File [] files = f.listFiles(new JarFilter());
    byte [] resource = null;
    for (int i = 0; i < files.length; i++) {
      try {
        //System.out.println("Locating " + className + " in jar file: " + files[i].getName());
        JarResources d = new JarResources(files[i]);
        resource =  d.getResource(className);
        if (resource != null) {
          break;
        }
      } catch (Exception e) {
        //System.out.println("ERROR: " + e.getMessage());
      }
    }

    return resource;

  }

  public void finalize() {
    System.out.println("In NavajoClassLoader finalize(): Killing class loader");
  }
}