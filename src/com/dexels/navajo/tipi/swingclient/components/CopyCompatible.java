package com.dexels.navajo.tipi.swingclient.components;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public interface CopyCompatible {
  public Object copyObject();
  public String getConstraint();
  public void setConstraint(String name);
}
