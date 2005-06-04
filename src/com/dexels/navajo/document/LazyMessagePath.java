package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Frank Lyaruu
 * @version $Id$
 */

public interface LazyMessagePath extends java.io.Serializable {

  public Object getRef();
  public void setStartIndex(int i);
  public void setEndIndex(int i);
  public int getStartIndex();
  public int getEndIndex();
  public void setTotalRows(int i);
}