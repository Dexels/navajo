package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.client.NavajoClient;
import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class NavajoMap implements Mappable {

  public String doSend;
  public String username = "";
  public String password = "";
  public String server;
  public int integerProperty;
  public String stringProperty;
  public boolean booleanProperty;
  public Date dateProperty;
  public String propertyName;
  public MessageMap message;
  public MessageMap [] messages;
  public String messagePointer;
  public boolean exists;
  public boolean append;

  private Navajo inDoc;
  private Navajo outDoc;
  private NavajoClient nc;
  private Property currentProperty;
  private String currentFullName;
  private Access access;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.access = access;
    nc = new NavajoClient();
    try {
      outDoc = NavajoFactory.getInstance().createNavajo();
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }

  public void store() throws MappableException, UserException {

  }

  /**
   * Set this to a valid message path if the result of the webservices needs to be appended.
   * If messageOffset = "/" the entire result will be appended to the current output message pointer.
   *
   * @param b
   * @throws UserException
   *
   * TODO: FINISH THIS. IMPLEMENT CLONE METHOD IN MESSAGE IMPLEMENTATION(!!)
   */
  public void setAppend(String messageOffset) throws UserException {
    try {
        Navajo currentDoc = access.getOutputDoc();
        Message currentMsg = access.getCurrentOutMessage();
        ArrayList list = (messageOffset.equals(Navajo.MESSAGE_SEPARATOR) ?
                          inDoc.getAllMessages() : inDoc.getMessages(messageOffset));
        for (int i = 0; i < list.size(); i++) {
          Message inMsg = (Message) list.get(i);
          // Clone message and append it to currentMsg if it exists, else directly under currentDoc.
        }
    } catch (NavajoException ne) {
      throw new UserException(-1, ne.getMessage());
    }
  }

  public void setPropertyName(String fullName) throws UserException {
    currentFullName = fullName;
    String propName = com.dexels.navajo.mapping.XmlMapperInterpreter.getStrippedPropertyName(fullName);
    try {
      currentProperty = outDoc.getProperty(fullName);
      if (currentProperty == null) {
          System.out.println("CONSTRUCTING NEW PROPERTY: " + fullName);
          currentProperty = NavajoFactory.getInstance().createProperty(outDoc, propName, Property.STRING_PROPERTY, "", 25, "", Property.DIR_IN);
      } else {
        System.out.println("FOUND EXISTING PROPERTY: " + fullName);
      }
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }

  public void setIntegerProperty(int i) throws UserException {
     System.out.println("in setIntegerProperty() : i = " + i);
     currentProperty.setType(Property.INTEGER_PROPERTY);
     currentProperty.setValue(i+"");
     addProperty(currentFullName, currentProperty);
  }

  public void setStringProperty(String s) throws UserException {
    currentProperty.setType(Property.STRING_PROPERTY);
    if (s != null)
      currentProperty.setValue(s);
    else
      currentProperty.setValue("null");
    addProperty(currentFullName, currentProperty);
  }

  public void setBooleanProperty(boolean b) throws UserException {
    currentProperty.setType(Property.BOOLEAN_PROPERTY);
    currentProperty.setValue(b+"");
    addProperty(currentFullName, currentProperty);
  }

  public void setDateProperty(Date d) throws UserException {
    System.out.println("setDateProperty() = " + d);
    currentProperty.setType(Property.DATE_PROPERTY);
    if (d != null)
      currentProperty.setValue(com.dexels.navajo.util.Util.formatDate(d));
    else
      currentProperty.setValue("");
    addProperty(currentFullName, currentProperty);
  }

  public void setUsername(String u) {
    this.username = u;
  }

  public void setPassword(String u) {
    this.password = u;
  }

  public void setServer(String u) {
    this.server = u;
  }

  public void setDoSend(String method) throws UserException {
    try {
      System.out.println("in setDoSend(), method = " + method + ", server = " +
                          server + ", username = " + username + ", password = " + password);
      inDoc = nc.doSimpleSend(outDoc, server, method, username, password, -1, false);
      outDoc = inDoc;
      if (inDoc.getMessage("error") != null) {
          throw new UserException(-1, "ERROR while accessing webservice: " + method + ":: " + inDoc.getMessage("error").getProperty("message").getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new UserException(-1, e.getMessage());
    }
  }

  public int getIntegerProperty(String fullName) throws UserException {
    Property p = inDoc.getProperty(fullName);
    if (p != null) {
      if (p.getType().equals(Property.INTEGER_PROPERTY) && !p.getValue().equals(""))
        return Integer.parseInt(p.getValue());
      else
        throw new UserException(-1, "Empty integer property: " + fullName);
    } else
      throw new UserException(-1, "Property " + fullName + " does not exists in response document");
  }

  public String getStringProperty(String fullName) throws UserException {
    Property p = inDoc.getProperty(fullName);
    if (p != null) {
        return p.getValue();
    } else
      throw new UserException(-1, "Property " + fullName + " does not exists in response document");
  }

  public boolean getExists(String fullName) throws UserException {
    Property p = inDoc.getProperty(fullName);
    return (p != null);
  }

  public Date getDateProperty(String fullName) throws UserException {
    Property p = inDoc.getProperty(fullName);
    if (p != null) {
      if (p.getType().equals(Property.DATE_PROPERTY) && !p.getValue().equals(""))
        return com.dexels.navajo.util.Util.getDate(p.getValue());
      else
        throw new UserException(-1, "Empty date property: " + fullName);
    } else
      throw new UserException(-1, "Property " + fullName + " does not exists in response document");
  }

  public void setMessagePointer(String m) {
    this.messagePointer = m;
  }

  public MessageMap getMessage() throws UserException {
    Message msg = inDoc.getMessage(messagePointer);
    if (msg == null)
      throw new UserException(-1, "Could not find message: " + messagePointer + " in response document");
    else {
      MessageMap mm = new MessageMap();
      mm.setMsg(msg);
      return mm;
    }
  }

  public MessageMap [] getMessages() throws UserException {
    try {
      ArrayList all = inDoc.getMessages(messagePointer);
      if ((all == null))
        throw new UserException(-1, "Could not find messages: " + messagePointer + " in response document");
      messages = new MessageMap[all.size()];
      for (int i = 0; i < all.size(); i++) {
        MessageMap msg = new MessageMap();
        msg.setMsg((Message) all.get(i));
        messages[i] = msg;
      }
      return messages;
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }

  /**
   * Dummy methods to support introspection of studio!!!!!
   * @return
   */
   public String getStringProperty() {
    return "";
  }

  public int getIntegerProperty() {
    return -1;
  }

   public Date getDateProperty() {
    return new java.util.Date();
   }

  public void kill() {

  }

  private void addProperty(String fullName, Property p) throws UserException {

    try {
      Message msg = com.dexels.navajo.mapping.XmlMapperInterpreter.getMessageObject(currentFullName, null,
                                                                                    false, outDoc, false, "");
      String propName = p.getName();
      msg.addProperty(p);
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }

  }

  public boolean isExists() {
    return false;
  }
}
