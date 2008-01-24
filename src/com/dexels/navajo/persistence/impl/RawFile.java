package com.dexels.navajo.persistence.impl;

import java.io.*;

import com.dexels.navajo.persistence.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class RawFile implements Persistable {

  public RawFile() {
  }

  private String content;

  public String persistenceKey() {
    return content.hashCode()+"";
  }

  public void setFile(File f) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        char [] c = new char[(int) f.length()];
        reader.read(c, 0, (int) f.length());
        content = String.copyValueOf(c);
        reader.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  public String getContent() {
    return content;
  }

}