package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class Test {

  public Test() {
  }

  public static void main(String args[]) {
    String path = "/com/navajo/dexels/Aap.java";
    String newp = path.substring("/com/navajo/dexels/".length(), path.length());
    System.out.println("newp = " + newp);
  }
}