package com.dexels.navajo.document.nanoimpl;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * <p>$Id$</p>
 * @author Frank Lyaruu
 * @version $Revision$
 * @deprecated
 */
@Deprecated
public final class SelectionImpl extends BaseSelectionImpl implements Selection, NanoElement {

   public SelectionImpl(Navajo n, String name, String value, boolean isSelected) {
       super(n,name,value,isSelected);
  }
  public SelectionImpl(Navajo n) {
    super(n);
  }

  public void fromXml(XMLElement x) {
    String attr = (String)x.getAttribute("selected");
    isSelected = (attr!=null && !attr.equals("0"));
    name = (String)x.getAttribute("name");
    value = (String)x.getAttribute("value");
    //System.err.println("name = " + name);
  }

  public XMLElement toXml(XMLElement parent) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("option");
    x.setAttribute("name",name);
    x.setAttribute("value",value==null?"-1":value);
    x.setAttribute("selected",isSelected?"1":"0");
    return x;
  }


  public Object getRef() {
    return toXml(null);
  }
public XMLElement toXml() {
    return toXml(null);
}
public final void writeComponent(Writer w) throws IOException {
    toXml().write(w);
}
}

// EOF $RCSfile$ //
