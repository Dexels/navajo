package com.dexels.navajo.document;
import nanoxml.*;
import java.util.*;

public class MethodImpl extends BaseNode implements Method {
  private ArrayList myRequiredMessages = new ArrayList();
  private String myName = "";
  private Message myParent = null;

  public MethodImpl(Navajo n) {
    super(n);
  }
  public MethodImpl(Navajo n, String name) {
    super(n);
    myName = name;
  }

  public ArrayList getAllRequired() {
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

  public Method copy(Navajo n) {

    Method m = Navajo.createMethod(n,getName());
    m.setAllRequired(getAllRequired());
    return m;
  }

  public String getPath() {
    if (myParent!=null) {
      return myParent.getPath()+"/"+getName();
    } else {
      return "/"+getName();
    }
  }

  }