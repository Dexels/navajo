package com.dexels.navajo.document;

import java.util.*;
import nanoxml.*;

public interface Method {
  public ArrayList getAllRequired();
  public String getName();
  public XMLElement toXml(XMLElement parent);
  public void fromXml(XMLElement e);
  public Message getParent();
  public void setParent(Message m);
  public String getPath();
  public Method copy(Navajo n);
  public void setAllRequired(ArrayList al);
}