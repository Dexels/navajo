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
import java.util.*;
import nanoxml.*;

public interface Message {
  public static final String MSG_DEFINITION = "message";
  public static final String MSG_NAME = "name";
  public static final String MSG_PARAMETERS_BLOCK = "__parms__";
  public static final String MSG_TYPE_NORMAL = "normal";
  public static final String MSG_TYPE_ARRAY = "array";
  public static final String MSG_TYPE_ARRAY_ELEMENT = "array_element";
  public static final String MSG_MODE_LAZY = "lazy";


  public String getName();
  public void setName(String name);
  public String getType();
  public void setType(String name);
  public int getIndex();
  public void setIndex(int index);
  public String getMode();
  public void setMode(String name);
  public ArrayList getAllMessages();
  public int getChildMessageCount();
  public ArrayList getAllProperties();
  public void addProperty(Property p);
  public Message getByPath(String path);
  public ArrayList getMessages(String regexp);
  public void addArrayMessage(Message m);
  public void addMessage(Message m);
  public Message getMessage(String name);
  public Property getProperty(String s);
  public XMLElement toXml(XMLElement parent);
  public void fromXml(XMLElement e);
  public Navajo getRootDoc();
  public void setRootDoc(Navajo n);
  public Message copy(Navajo n);
  public void prune();
  public MessageMappable getMessageMap();
  public void setMessageMap(MessageMappable m);
  public Message getMessage(int i);
  public Message getParent();
  public void setParent(Message m);
  public String getPath();
  public int getStartIndex();
  public int getEndIndex();
  public void setStartIndex(int i);
  public void setEndIndex(int i);
  public Message getMessage(String name, int index);
  public Property getPropertyByPath(String path);
  public void removeChildMessage(Message msg);
  public XMLElement generateTml(Header h);

  }