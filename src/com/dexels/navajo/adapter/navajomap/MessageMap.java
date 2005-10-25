package com.dexels.navajo.adapter.navajomap;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.io.StringWriter;
import com.dexels.navajo.document.types.Binary;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class MessageMap implements Mappable {

  public int integerProperty;
  public String stringProperty;
  public Date dateProperty;
  public Binary binaryProperty;
  public MessageMap message;
  public MessageMap [] messages;
  public String messagePointer;
  public Object property;

  private Message msg;

  public MessageMap() {
  }

  public void setMsg(Message msg) {
    this.msg = msg;
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }

  public void store() throws MappableException, UserException {

  }

  public String getStringProperty() {
    return "";
  }

  public int getIntegerProperty() {
    return -1;
  }

   public Date getDateProperty() {
    return new java.util.Date();
   }

   public void setIntegerProperty(String fullName) throws UserException {

   }

   private void propertDoesNotExistException(String fullName) throws UserException {
     StringWriter msgContent = new StringWriter();
     msg.write(msgContent);
     throw new UserException( -1,
                             "Property " + fullName +
                             " does not exists in response document(" +
                            msg.getName() + "), message content:\n"+msgContent.toString());

   }

   public int getIntegerProperty(String fullName) throws UserException {
    Property p = msg.getProperty(fullName);
    if (p != null) {
      if (p.getType().equals(Property.INTEGER_PROPERTY) && !p.getValue().equals(""))
        return Integer.parseInt(p.getValue());
      else
        throw new UserException(-1, "Empty integer property: " + fullName);
    } else
      propertDoesNotExistException(fullName);
      return -1;
  }

   public Object getProperty(String fullName) throws Exception {
	   Property p = msg.getProperty(fullName);
	   if (p != null) {
	        return p.getTypedValue();
	    } else {
	      propertDoesNotExistException(fullName);
	      return "";
	    }
   }
   
  /**
   * Determine whether a property or message object exists within the response document.
   * If messagePointer is set, search is relative from messagePointer.
   *
   * @param fullName
   * @return
   * @throws UserException
   */
  public boolean getExists(String fullName) throws UserException {

    try {
      Property p = msg.getProperty(fullName);
      if (p != null)
       return true;
     else
       return false;
    } catch (Exception e) {
        return false;
    }
  }

  public String getStringProperty(String fullName) throws UserException {
    Property p = msg.getProperty(fullName);
    if (p != null) {
        return p.getValue();
    } else {
      propertDoesNotExistException(fullName);
      return "";
    }
  }

  public Date getDateProperty(String fullName) throws UserException {
    Property p = msg.getProperty(fullName);
    if (p != null) {
      if (p.getType().equals(Property.DATE_PROPERTY)) {
        if (p.getValue() != null && !p.getValue().equals(""))
          return com.dexels.navajo.util.Util.getDate(p.getValue());
        else
          return null;
      }
      else
        throw new UserException(-1, "Invalid date property: " + fullName + "(string value = " + p.getValue() + ", type = " + p.getType() + " )");
    } else
      propertDoesNotExistException(fullName);
      return null;
  }

  public Binary getBinaryProperty(String fullName) throws UserException {
  Property p = msg.getProperty(fullName);
  if (p != null) {
    if (p.getType().equals(Property.BINARY_PROPERTY)) {
      if (p.getValue() != null && !p.getValue().equals(""))
        return (Binary) p.getTypedValue();
      else
        return null;
    }
    else
      throw new UserException(-1, "Invalid date property: " + fullName + "(string value = " + p.getValue() + ", type = " + p.getType() + " )");
  } else
    propertDoesNotExistException(fullName);
    return null;
}


  public void setMessagePointer(String s) {
    this.messagePointer = s;
  }


    public MessageMap getMessage() throws UserException {
    Message m = msg.getMessage(messagePointer);
    if (m == null)
      throw new UserException(-1, "Could not find message: " + messagePointer + " in response document (" + msg.getName() + ")");
    else {
      MessageMap mm = new MessageMap();
      mm.setMsg(m);
      return mm;
    }
  }

  public MessageMap [] getMessages() throws UserException {
    try {
      ArrayList all = msg.getMessages(messagePointer);
      if ((all == null))
        throw new UserException(-1, "Could not find messages: " + messagePointer + " in response document (" + msg.getName() + ")");
      messages = new MessageMap[all.size()];
      for (int i = 0; i < all.size(); i++) {
        MessageMap m = new MessageMap();
        m.setMsg((Message) all.get(i));
        messages[i] = m;
      }
      return messages;
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }


  public void kill() {

  }
}
