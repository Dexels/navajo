package com.dexels.navajo.server;

import java.io.InputStream;
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