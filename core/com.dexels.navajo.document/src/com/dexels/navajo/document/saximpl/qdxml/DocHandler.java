/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.saximpl.qdxml;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public interface DocHandler {
  public void startElement(String tag,Map<String,String> h) throws Exception;
  public void endElement(String tag) throws Exception;
  public void startDocument() throws Exception;
  public void addComment(String c);
  public void endDocument() throws Exception;
  public void text(Reader r) throws Exception;
  public String quoteStarted(int quoteCharacter, Reader r, String attributeName,String tagName, StringBuilder buffer) throws IOException ;
  public QDParser getParser();
}
