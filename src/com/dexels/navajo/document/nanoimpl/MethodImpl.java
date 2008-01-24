package com.dexels.navajo.document.nanoimpl;
import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;
/**
 * 
 * @author Frank Lyaruu
 * @deprecated
 */
@Deprecated
public final class MethodImpl extends BaseMethodImpl implements Method, NanoElement {
  private ArrayList<Required> myRequiredMessages = new ArrayList<Required>();

  public MethodImpl(Navajo n) {
    super(n);
  }
  public MethodImpl(Navajo n, String name) {
    super(n);
    myName = name;
  }


  public final XMLElement toXml(XMLElement parent) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("method");
    x.setAttribute("name",myName);
    if (myServer!=null) {
      x.setAttribute("server",myServer);
    }

     for (int i = 0; i < myRequiredMessages.size(); i++) {
     	RequiredImpl req = (RequiredImpl) myRequiredMessages.get(i);
     	x.addChild(req.toXml(x));
    }
    return x;
  }

  public final void fromXml(XMLElement e) {
    myRequiredMessages.clear();
    myName = (String)e.getAttribute("name");
    myServer = (String)e.getAttribute("server");
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = e.getChildren().get(i);
      if (child.getName().equals("required")) {
//        String msg = (String)child.getAttribute("message");
//        if (msg!=null) {
//          myRequiredMessages.add(msg);
//        }
      	RequiredImpl req = new RequiredImpl();
      	req.fromXml(child);
      	myRequiredMessages.add(req);
      } else {
        throw new RuntimeException("Strange tag found within method: "+child.getName());
      }
    }
  }

  public final Object getRef() {
    return toXml(null);
  }
public XMLElement toXml() {
    return toXml(null);
    }
public final void writeComponent(Writer w) throws IOException {
    toXml().write(w);
}

  }