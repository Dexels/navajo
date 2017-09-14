package com.dexels.navajo.document.stream.xml;

import java.util.Map;

public interface XmlInputHandler {
  public int startElement(String tag,Map<String,String> h);
  public int endElement(String tag);
  public int startDocument() ;
  public int endDocument();
  public int text(String r);
}
