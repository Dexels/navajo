package com.dexels.navajo.document.nanoimpl;

import com.dexels.navajo.document.*;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TableMessageImpl extends BaseNode
    implements Message {
  private String myName = null;

  private final Map valueMap = new HashMap();

  private final List columnList = new ArrayList();
  private final Map columnMap = new HashMap();
  private Message myParent = null;

//  private final Map messageCacheMap = new HashMap();

  public TableMessageImpl(Navajo n) {
    super(n);
  }

  public TableMessageImpl(Navajo n, String name) {
    super(n);
    myName = name;
//    myType = Message.MSG_TYPE_SIMPLE;
  }


//  public Property getPropertyAtRow(int row, String name) {
//
//    Map rowMap = (Map)valueMap.get(new Integer(row));
//    if (rowMap==null) {
//      return null;
//    }
//    String value = (String)rowMap.get(name);
//    Property pp = getColumnProperty(name).copy(getRootDoc());
//    pp.setValue(value);
//    return pp;
//  }



  private Property getColumnProperty(String name) {
    return (Property)columnMap.get(name);
  }

  public void setParent(Message m) {
    myParent = m;
  }

  public Message addElement(Message m) {
    return null;
  }

  public void addMessage(Message m, int index) throws NavajoException {
  }

  public Message addMessage(Message m, boolean overwrite) {
    throw new UnsupportedOperationException("Can not addMessage(msg,bool overwrite) in TableMessageImpl");
  }

  public Message addMessage(Message m) {
    return null;
  }

  public void addProperty(Property p) {
  }

  public boolean contains(String name) {
    // really this is not applicable for table/array messages
    return false;
  }

  public Message copy() throws NavajoException {
    throw new UnsupportedOperationException("Oops: Copy for tablemessage is not yet implemented");
  }

  public Message copy(Navajo n) {
    throw new UnsupportedOperationException("Oops: Copy for tablemessage is not yet implemented");
  }

  public ArrayList getAllMessages() {
    return null;
  }

  public ArrayList getAllProperties() {
    return null;
  }

  public int getArraySize() {
    return valueMap.size();
  }

  public int getCurrentTotal() {
    return 0;
  }

  public String getFullMessageName() {
    return "";
  }

  public int getIndex() {
    return 0;
  }

  public Message getMessage(String name) {
    throw new UnsupportedOperationException("Should not call getMessage(String) on table messages");
   }

  public Message getMessage(int index) {
//    Message m = (Message)messageCacheMap.get(new Integer(index));
//    if (m!=null) {
//      return m;
//    }
    return null;
  }

  public ArrayList getMessages(String regularExpression) throws NavajoException {
    return null;
  }

  public String getMode() {
    return "";
  }

  public String getName() {
    return myName;
  }

  public Message getParentMessage() {
    return null;
  }

  public Property getPathProperty(String property) {
    return null;
  }

  public ArrayList getProperties(String regularExpression) throws
      NavajoException {
    return null;
  }

  public Property getProperty(String name) {
    return null;
  }

  public Object getRef() {
    return "";
  }

  public Navajo getRootDoc() {
    return null;
  }

  public String getType() {
    return "";
  }

  public boolean isArrayMessage() {
    return false;
  }

  public boolean isEqual(Message o, String skipProperties) {
    return false;
  }

  public boolean isEqual(Message o) {
    return false;
  }

  public void refreshExpression() throws NavajoException {
  }

  public void removeMessage(Message m) {
  }

  public void removeProperty(Property p) {
  }

  public void setArraySize(int c) {
  }

  public void setCondition(String condition) {
  }

  public void setCurrentTotal(int aap) {
    //nop
  }

  public void setIndex(int i) {
  }

  public void setLazyRemaining(int c) {
  }

  public void setLazyTotal(int c) {
  }

  public void setMessageMap(MessageMappable m) {
  }

  public void setMode(String mode) {
    // ignore: Always table
  }

  public void setName(String name) {
    myName = name;
  }


  public void setType(String s) {
  }

  public void write(OutputStream stream) {
  }

  public void write(Writer writer) {
  }

  public void fromXml(XMLElement e) {
    Vector v = e.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement child = (XMLElement)v.get(i);
      if (child.getName().equals("columns")) {
        loadColumns(child);
      }
      if (child.getName().equals("values")) {
        loadValues(child);
      }
    }
  }

  private void loadColumns(XMLElement xe) {
    Vector v = xe.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement child = (XMLElement)v.get(i);
      PropertyImpl pi = new PropertyImpl(getRootDoc(),"name");
      pi.fromXml(child);
      columnList.add(pi);
      columnMap.put(pi.getName(),pi);
      System.err.println("Loaded column");
    }

  }
  private void loadValues(XMLElement xe) {
    Vector v = xe.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement child = (XMLElement)v.get(i);
      int row = child.getIntAttribute("row");
      String column = child.getStringAttribute("column");
      String value = child.getStringAttribute("value");
      Map rowMap = (Map)valueMap.get(new Integer(row));
      if (rowMap==null) {
        rowMap = new HashMap();
        valueMap.put(new Integer(row),rowMap);
      }
      rowMap.put(column,value);
      System.err.println("Loaded value");
    }

  }

  public XMLElement toXml(XMLElement parent) {
    XMLElement m = new CaseSensitiveXMLElement();
    m.setAttribute("name", myName);
    m.setAttribute("type",MSG_TYPE_TABLE);
    m.setName("message");
    return m;
  }

//  public static void main(String[] args) throws Exception {
//    System.setProperty("com.dexels.navajo.DocumentImplementation",
//                       "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
//     Navajo n = NavajoFactory.getInstance().createNavajo(new FileInputStream("c:/testtable.xml"));
//     n.write(System.err);
//     Message m = n.getMessage("aap");
//     System.err.println("\n=======================");
//     for (int i = 0; i < m.getArraySize(); i++) {
//       Message mmm = m.getMessage(i);
//       Property p = mmm.getProperty("noot");
//       System.err.println(">>> "+p.getType());
//       Property pp = m.getPropertyAtRow(i,"noot");
//       System.err.println(">>>> "+pp.getType());
//       System.err.println("Message!");
//     }
//  }

  public void addMessage(int index) {
  }


  /**
   * Add empty message at the end
   */
  public void addMessage() {
  }
  public Message getDefinitionMessage() {
    throw new UnsupportedOperationException("Can not get definition message in JAXPIMPL");
  }

}
