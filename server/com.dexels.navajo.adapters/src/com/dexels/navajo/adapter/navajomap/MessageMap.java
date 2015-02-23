package com.dexels.navajo.adapter.navajomap;


import java.util.ArrayList;
import java.util.Date;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

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

  @Override
public void load(Access access) throws MappableException, UserException {

  }

  @Override
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

   /**
 * @param fullName  
 */
public void setIntegerProperty(String fullName) {

   }

   private void propertDoesNotExistException(String fullName) throws UserException {
     throw new UserException( -1,
                             "Property " + fullName +
                             " does not exists in response document(" +
                            msg.getName() + "), message content: [[DISABLED]]\n");

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

   public Property getPropertyObject(String fullName) throws NavajoException {
	   Property p = msg.getProperty(fullName);
	   return p;
   }
   
   public Object getProperty(String fullName) throws NavajoException, UserException {
	   Property p = msg.getProperty(fullName);
		  if ( p == null ) {
			  propertDoesNotExistException(fullName);
			  return "";
		  }
		  if ( p.getType().equals(Property.SELECTION_PROPERTY )) {
			  if ( p.getSelected() != null ) {
				  return p.getSelected().getValue();
			  } else {
				  return null;
			  }
		  } else {
			  return p.getTypedValue();
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
      return p != null;
    } catch (Exception e) {
        return false;
    }
  }

  public String getStringProperty(String fullName) throws UserException, NavajoException {
	 return (String) getProperty(fullName);
  }

  public Date getDateProperty(String fullName) throws UserException {
    Property p = msg.getProperty(fullName);
    if (p != null) {
      if (p.getType().equals(Property.DATE_PROPERTY)) {
        if (p.getValue() != null && !p.getValue().equals(""))
          return (Date) p.getTypedValue();
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
      ArrayList<Message> all = msg.getMessages(messagePointer);
      if ((all == null))
        throw new UserException(-1, "Could not find messages: " + messagePointer + " in response document (" + msg.getName() + ")");
      messages = new MessageMap[all.size()];
      for (int i = 0; i < all.size(); i++) {
        MessageMap m = new MessageMap();
        m.setMsg(all.get(i));
        messages[i] = m;
      }
      return messages;
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }


  @Override
public void kill() {

  }
}
