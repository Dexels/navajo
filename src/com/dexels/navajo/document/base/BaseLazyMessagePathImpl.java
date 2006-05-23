package com.dexels.navajo.document.base;

import com.dexels.navajo.document.*;

/**
 * @deprecated
 * @author Frank Lyaruu
 * Prutso ergo sum
 */
public abstract class BaseLazyMessagePathImpl extends BaseNode implements LazyMessagePath {

  private String messagePath;
  private int startIndex = -1;
  private int endIndex = -1;
  private int totalRows = 0;

  public BaseLazyMessagePathImpl(Navajo n, String path, int startIndex, int endIndex, int total) {
    super(n);
    messagePath = path;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.totalRows = total;
  }

  public BaseLazyMessagePathImpl(Navajo n) {
    super(n);
  }


  public void setStartIndex(int i) {
    startIndex = i;
  }

  public void setEndIndex(int i) {
    endIndex = i;
  }

  public void setTotalRows(int i) {
    totalRows = i;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }
}