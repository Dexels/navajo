package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface LazyMessagePath extends java.io.Serializable {

  public Object getRef();
  public void setStartIndex(int i);
  public void setEndIndex(int i);
  public int getStartIndex();
  public int getEndIndex();
  public void setTotalRows(int i);
}