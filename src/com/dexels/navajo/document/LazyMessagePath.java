package com.dexels.navajo.document;

import nanoxml.*;

public class LazyMessagePath extends BaseNode {

  private String messagePath;
  private int startIndex = -1;
  private int endIndex = -1;

  public LazyMessagePath(Navajo n, String path, int startIndex, int endIndex) {
    super(n);
    messagePath = path;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public LazyMessagePath(Navajo n) {
    super(n);
  }

  public XMLElement toXml(XMLElement parent) {
      XMLElement lazy = new CaseSensitiveXMLElement();
      lazy.setName("lazymessage");
      lazy.setAttribute("name",messagePath);
      lazy.setAttribute("startindex",""+startIndex);
      lazy.setAttribute("endindex",""+endIndex);
//      parent.addChild(lazy);
    return lazy;
  }

  public void setStartIndex(int i) {
    startIndex = i;
  }

  public void setEndIndex(int i) {
    endIndex = i;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }
}