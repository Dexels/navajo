package com.dexels.navajo.document.nanoimpl;
import java.util.*;

import com.dexels.navajo.document.*;

public class MethodImpl extends BaseNode implements Method {
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

  public ArrayList getRequiredMessages() {
    return (ArrayList)myRequiredMessages.clone();
  }

  public void setAllRequired(ArrayList al) {
    myRequiredMessages = al;
  }

  public String getName() {
    return myName;
  }
  public XMLElement toXml(XMLElement parent) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("method");
    x.setAttribute("name",myName);
    for (int i = 0; i < myRequiredMessages.size(); i++) {
      XMLElement req = new CaseSensitiveXMLElement();
      req.setName("required");
      System.err.println("Required: "+myRequiredMessages.get(i));
      req.setAttribute("message",myRequiredMessages.get(i));
    }
    return x;
  }

  public String getDescription() {
    return myDescription;
  }

  public void setDescription(String s) {
    myDescription = s;
  }

  public void fromXml(XMLElement e) {
    myRequiredMessages.clear();
    myName = (String)e.getAttribute("name");
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

  public void setParent(Message m ) {
    myParent = m;
  }

  public Message getParent() {
    return myParent;
  }

  public void setServer(String s) {
    myServer = s;
  }

  public String getServer() {
    return myServer;
  }

  public Method copy(Navajo n) {
/** @todo SERVER?! */
    MethodImpl m = (MethodImpl)NavajoFactory.getInstance().createMethod(n,getName(),"");
    m.setAllRequired(getRequiredMessages());
    return m;
  }

  public String getPath() {
    if (myParent!=null) {
      return myParent.getFullMessageName()+"/"+getName();
    } else {
      return "/"+getName();
    }
  }

  public Object getRef() {
    return toXml(null);
  }
  public void setName(String name) {
    myName = name;
  }
  public void addRequired(String message) {
    myRequiredMessages.add(message);
  }
  public void addRequired(Message message) {
    myRequiredMessages.add(message.getFullMessageName());
    /**@todo Implement this com.dexels.navajo.document.Method abstract method*/
  }

  }