package com.dexels.navajo.nanodocument;
import nanoxml.*;
import java.util.*;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class RootMessage extends MessageImpl {

  public RootMessage(Navajo n) {
    super(n);
  }


  public XMLElement toXml(XMLElement parent) {
//    throw new RuntimeException("Use generateTML instead");
    return super.toXml(parent);
  }
  public String getPath() {
      return getName();
  }
  public Message getByPath(String path) {
    System.err.println("Parsing path: (RootMessage): "+path);
    if (!path.startsWith("/")) {
      System.err.println("ERROR: Path definititions should start with /");
      return null;
    }
    else
      return super.getByPath(path.substring(1));
  }
}