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

public class FileInputStreamReader implements InputStreamReader {

  public InputStream getResource(String name) {
    try {
      return new java.io.FileInputStream(name);
    } catch (Exception ioe) {
      ioe.printStackTrace();
      return null;
    }
  }
}