package com.dexels.navajo.persistence.impl;

import com.dexels.navajo.persistence.Persistable;
import java.io.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
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
        content = content.copyValueOf(c);
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  public String getContent() {
    return content;
  }

  public static void main(String args[]) {
    RawFile rf = new RawFile();
    File f = new File("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/cache/persistent-tml-ProcessQueryCountries__487457929.xml");
    System.out.println(f.length()+"");
    rf.setFile(f);
    System.out.println(rf.getContent());
  }
}