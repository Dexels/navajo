package com.dexels.navajo.server;

import java.io.InputStream;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.net.URL;

public final class ClassloaderInputStreamReader implements InputStreamReader {

  public final InputStream getResource(String name) {
      try {
        URL u = getClass().getClassLoader().getResource(name);
        if (u==null) {
          return null;
        }
        return u.openStream();
      } catch (java.io.IOException ioe) {
        ioe.printStackTrace();
        return null;
      }
  }

  public static void main(String [] args) {
    ClassloaderInputStreamReader aap = new ClassloaderInputStreamReader();
    InputStream s = aap.getResource("aap0");
    System.out.println("s = " + s);
  }

}