package com.dexels.navajo.tipi;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiConstraintEditor {
  public void parseString(String s);
  public Object getConstraint();
  public void setConstraint(Object o);
  public String getConstraintString();
}