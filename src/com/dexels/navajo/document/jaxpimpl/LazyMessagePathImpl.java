package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class LazyMessagePathImpl {
  private String messagePath;
  private int startIndex = -1;
  private int endIndex = -1;

  public LazyMessagePathImpl(Navajo n, String path, int startIndex, int endIndex) {
    super();
    messagePath = path;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public LazyMessagePathImpl(Navajo n) {
    super();
  }

//  public XMLElement toXml(XMLElement parent) {
//      XMLElement lazy = new CaseSensitiveXMLElement();
//      lazy.setName("lazymessage");
//      lazy.setAttribute("name",messagePath);
//      lazy.setAttribute("startindex",""+startIndex);
//      lazy.setAttribute("endindex",""+endIndex);
//    return lazy;
//  }

  public Object getRef() {
    return null;
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