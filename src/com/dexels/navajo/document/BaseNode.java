package com.dexels.navajo.document;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

import nanoxml.*;

public class BaseNode {
  protected Navajo myDocRoot;
  public BaseNode(Navajo n) {
    myDocRoot = n;
  }

  public Navajo getRootDoc() {
    return myDocRoot;
  }


  public void setRootDoc(Navajo n) {
    myDocRoot = n;
  }

  public XMLElement toXml(XMLElement parent) {
    return new XMLElement();
  }

  public void fromXml(XMLElement e) {
  }
}