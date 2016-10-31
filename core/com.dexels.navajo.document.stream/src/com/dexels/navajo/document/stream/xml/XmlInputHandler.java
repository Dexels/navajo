package com.dexels.navajo.document.stream.xml;

import java.util.Map;

public interface XmlInputHandler {
  public void startElement(String tag,Map<String,String> h);
  public void endElement(String tag);
  public void startDocument() ;
  public void endDocument();
  public void text(String r);
}
