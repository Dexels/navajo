package com.dexels.navajo.document.nanoimpl;
import java.util.*;

import com.dexels.navajo.document.*;

public final class MethodImpl extends BaseNode implements Method {
  private ArrayList myRequiredMessages = new ArrayList();
  private String myName = "";
  private Message myParent = null;
  private String myDescription = null;
  private String myServer;

  public MethodImpl(Navajo n) {
    super(n);
  }
  public MethodImpl(Navajo n, String name) {
    super(n);
    myName = name;
  }

  public final ArrayList getRequiredMessages() {
    return (ArrayList)myRequiredMessages.clone();
  }

  public final void setAllRequired(ArrayList al) {
    myRequiredMessages = al;
  }

  public final String getName() {
    return myName;
  }
  public final XMLElement toXml(XMLElement parent) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("method");
    x.setAttribute("name",myName);
    if (myServer!=null) {
      x.setAttribute("server",myServer);
    }

     for (int i = 0; i < myRequiredMessages.size(); i++) {
      XMLElement req = new CaseSensitiveXMLElement();
      req.setName("required");
      req.setAttribute("message",myRequiredMessages.get(i));
      x.addChild(req);
    }
    return x;
  }

  public final String getDescription() {
    return myDescription;
  }

  public final void setDescription(String s) {
    myDescription = s;
  }

  public final void fromXml(XMLElement e) {
    myRequiredMessages.clear();
    myName = (String)e.getAttribute("name");
    myServer = (String)e.getAttribute("server");
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement)e.getChildren().elementAt(i);
      if (child.getName().equals("required")) {
        String msg = (String)child.getAttribute("message");
        if (msg!=null) {
          myRequiredMessages.add(msg);
        }

      } else {
        throw new RuntimeException("Strange tag found within method: "+child.getName());
      }
    }
  }

  public final void setParent(Message m ) {
    myParent = m;
  }

  public final Message getParent() {
    return myParent;
  }

  public final void setServer(String s) {
    myServer = s;
  }

  public final String getServer() {
    return myServer;
  }

  public final Method copy(Navajo n) {
/** @todo SERVER?! */
    MethodImpl m = (MethodImpl)NavajoFactory.getInstance().createMethod(n,getName(),getServer());
    m.setAllRequired(getRequiredMessages());
    return m;
  }

  public final String getPath() {
    if (myParent!=null) {
      return myParent.getFullMessageName()+"/"+getName();
    } else {
      return "/"+getName();
    }
  }

  public final Object getRef() {
    return toXml(null);
  }
  public final void setName(String name) {
    myName = name;
  }

  public final void addRequired(String message) {
    myRequiredMessages.add(message);
  }

  public final void addRequired(Message message) {
    myRequiredMessages.add(message.getFullMessageName());
    /**@todo Implement this com.dexels.navajo.document.Method abstract method*/
  }

  }