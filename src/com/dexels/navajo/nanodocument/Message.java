package com.dexels.navajo.nanodocument;
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
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.document.*;

public interface Message {
  public static final String MSG_DEFINITION = "message";
  public static final String MSG_NAME = "name";
  public static final String MSG_PARAMETERS_BLOCK = "__parms__";
  public static final String MSG_INDEX = "index";
  public static final String MSG_TYPE_NORMAL = "normal";
  public static final String MSG_TYPE_ARRAY = "array";
  public static final String MSG_TYPE_ARRAY_ELEMENT = "array_element";
  public static final String MSG_MODE_LAZY = "lazy";
//  public static final String MSG_INDEX = "index";
  public static final String MSG_TYPE = "type";
  public static final String MSG_MODE = "mode";

  public static final String MSG_MODE_IGNORE = "ignore";

  public static final String MSG_LAZY_COUNT = "lazy_total";
  public static final String MSG_LAZY_REMAINING = "lazy_remaining";
  public static final String MSG_ARRAY_SIZE = "array_size";



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

  public void addElement(Message m);

  public void addMessage(Message m);

  public void addMessage(Message m, boolean b);

  public int getArraySize();

  public void setArraySize(int i);

  public boolean isArrayMessage();

  /** @todo FIX THIS */
  public Message getMessage(String name);

  public ArrayList getProperties(String regexp);

  /** @todo FIX THIS */
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
  /*
   * @deprecated
   */
  public Message getParentMessage();
  public void setParent(Message m);
  /*
   * @deprecated
   */
  public String getPath();
  public String getFullMessageName();
  public int getStartIndex();
  public int getEndIndex();
  public void setStartIndex(int i);
  public void setEndIndex(int i);
  public Message getMessage(String name, int index);
  /*
   * @deprecated
   */
  public Property getPropertyByPath(String path);
  public Property getPathProperty(String path);
  /*
   * @deprecated
   */
  public void removeChildMessage(Message msg);
  public XMLElement generateTml(Header h);
  public void removeMessage(Message msg);
  public void removeMessage(String s);
  }