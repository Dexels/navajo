package com.dexels.navajo.document.saximpl.qdxml;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public interface DocHandler {
  public void startElement(String tag,Map<String,String> h) throws Exception;
  public void endElement(String tag) throws Exception;
  public void startDocument() throws Exception;
  public void endDocument() throws Exception;
  public void text(Reader r) throws Exception;
  public String quoteStarted(int quoteCharacter, Reader r, String attributeName,String tagName, StringBuilder buffer) throws IOException ;
}
