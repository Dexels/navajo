package com.dexels.navajo.tipi.internal;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SystemPropertyRef {

  private final String name;

  public SystemPropertyRef(String name) {
    this.name = name;
  }

  public void setValue(Object value) {
    System.setProperty(name,""+value);
 }
}
