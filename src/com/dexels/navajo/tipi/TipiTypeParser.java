package com.dexels.navajo.tipi;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiTypeParser {

  public abstract Object parse(String expression);
  public String toString(Object o) {
    return o==null?"null":o.toString();
  }
}