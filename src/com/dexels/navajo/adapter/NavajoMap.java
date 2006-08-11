package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.adapter.navajomap.MessageMap;
import com.dexels.navajo.client.*;

import java.util.*;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.Money;

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
  public String username = null;
  public String password = null;
  public String server = null;

  /**
   * For each of the supported property types a corresponding field of
   * the appropriate type should exist
   */
  public boolean   booleanProperty;
  public int       integerProperty;
  public float     floatProperty;
  public String    stringProperty;
  public ClockTime clockTimeProperty;
  public Date      dateProperty;
  public Money     moneyProperty;
  public Binary	   binaryProperty;
  public Object    property;

  public String propertyName;
  public MessageMap message;
  public MessageMap [] messages;
  public String messagePointer;
  public boolean exists;
  public String append;
  // appendParms is used to append entire output doc of called webservice to param block.
  public String appendParms;
  public boolean sendThrough;
  /**
   * if useCurrentOutDoc is set, the NavajoMap will use the outDoc from the access object instead of creating a new one.
   */
  public boolean useCurrentOutDoc;
  public boolean breakOnConditionError = true;
  public String keyStore;
  public String keyPassword;
  public String compare = "";
  public String skipProperties = "";
  public boolean isEqual = false;

  private Navajo inDoc;
  private Navajo outDoc;
  //private NavajoClient nc;
  private Property currentProperty;
  private String currentFullName;
  protected Access access;
  protected NavajoConfig config;
  protected Navajo inMessage;
  protected Message msgPointer;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.access = access;
    this.config = config;
    this.inMessage = inMessage;
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
   *
   * (!)if messageOffset is '', the received inDoc document will become the new output document for the Navajo service.
   *    if messageOffset is '/', the messages of the received inDoc will be appended to the output document.
   */
  public final void setAppend(String messageOffset) throws UserException {

    if (messageOffset.equals("")) {
       access.setOutputDoc(inDoc);
       return;
    }

    try {
        Navajo currentDoc = access.getOutputDoc();
        Message currentMsg = access.getCurrentOutMessage();
        ArrayList list = null;
        // If append message equals '/'.
        if ( messageOffset.equals(Navajo.MESSAGE_SEPARATOR) ) {
        	list = inDoc.getAllMessages();
        } else if ( inDoc.getMessage(messageOffset) == null ) {
        	return;
        } else if ( inDoc.getMessage(messageOffset).getType().equals(Message.MSG_TYPE_ARRAY) ) {
        	list = new ArrayList();
        	list.add( inDoc.getMessage(messageOffset) );
        } else {
        	list = inDoc.getMessages(messageOffset);
        }
        	
        for (int i = 0; i < list.size(); i++) {
          Message inMsg = (Message) list.get(i);
          // Clone message and append it to currentMsg if it exists, else directly under currentDoc.
          //currentDoc.importMessage(inMsg);
          Message clone = inDoc.copyMessage(inMsg, currentDoc);
          if (currentMsg != null) {
            currentMsg.addMessage(clone, true);
          } else {
            currentDoc.addMessage(clone, true);
          }
        }
    } catch (NavajoException ne) {
      throw new UserException(-1, ne.getMessage());
    }
  }
  
  /**
   * @param b
   * @throws UserException
   *
   *  if messageOffset is '/', the messages of the received Doc will be appended to the root 
   *  param block or to the current param message.
   */
  public final void setAppendParms(String messageOffset) throws UserException {

    try {
    	Message parm = ( access.getCompiledScript().currentParamMsg == null ? 
    			         access.getInDoc().getMessage("__parms__") :
    			        	 access.getCompiledScript().currentParamMsg);
    			        	
        ArrayList list = null;
        // If append message equals '/'.
        if ( messageOffset.equals(Navajo.MESSAGE_SEPARATOR) ) {
        	list = inDoc.getAllMessages();
        } else if ( inDoc.getMessage(messageOffset) == null ) {
        	return;
        } else if ( inDoc.getMessage(messageOffset).getType().equals(Message.MSG_TYPE_ARRAY) ) {
        	list = new ArrayList();
        	list.add( inDoc.getMessage(messageOffset) );
        } else {
        	list = inDoc.getMessages(messageOffset);
        }
        	
        for (int i = 0; i < list.size(); i++) {
          Message inMsg = (Message) list.get(i);
          // Clone message and append it to currentMsg if it exists, else directly under currentDoc.
          //currentDoc.importMessage(inMsg);
          Message clone = inDoc.copyMessage(inMsg, parm.getRootDoc());
          parm.addMessage(clone, true);
        }
    } catch (NavajoException ne) {
      throw new UserException(-1, ne.getMessage());
    }
  }

  public final void setPropertyName(String fullName) throws UserException {
    currentFullName = ((messagePointer == null || messagePointer.equals("")) ? fullName : messagePointer + "/" + ((fullName.startsWith("/") ? fullName.substring(1) : fullName)));
    String propName = MappingUtils.getStrippedPropertyName(fullName);
    try {
      if (msgPointer != null)
        currentProperty = msgPointer.getProperty(fullName);
      else
        currentProperty = outDoc.getProperty(fullName);
      if (currentProperty == null) {
          //System.out.println("CONSTRUCTING NEW PROPERTY: " + fullName);
          currentProperty = NavajoFactory.getInstance().createProperty(outDoc, propName, Property.STRING_PROPERTY, "", 25, "", Property.DIR_IN);
      } else {
        //System.out.println("FOUND EXISTING PROPERTY: " + fullName);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new UserException(-1, e.getMessage());
    }
  }

  public final void setIntegerProperty(int i) throws UserException {
     //System.out.println("in setIntegerProperty() : i = " + i);
     currentProperty.setType(Property.INTEGER_PROPERTY);
     currentProperty.setValue(i+"");
     addProperty(currentFullName, currentProperty);
  }

  public final void setFloatProperty(double i) throws UserException {
     //System.out.println("in setFloatProperty() : i = " + i);
     currentProperty.setType(Property.FLOAT_PROPERTY);
     currentProperty.setValue(i+"");
     addProperty(currentFullName, currentProperty);
  }

  public final void setStringProperty(String s) throws UserException {
    currentProperty.setType(Property.STRING_PROPERTY);
    currentProperty.setValue(s);
    addProperty(currentFullName, currentProperty);
  }
  
  public final void setProperty(Object o) throws UserException {
	  currentProperty.setAnyValue(o);
	  addProperty(currentFullName, currentProperty);
  }

  public final void setBooleanProperty(boolean b) throws UserException {
    currentProperty.setType(Property.BOOLEAN_PROPERTY);
    currentProperty.setValue(b);
    addProperty(currentFullName, currentProperty);
  }

  public final void setClockTimeProperty(ClockTime d) throws UserException {
    //System.out.println("setClockTimeProperty() = " + d);
    currentProperty.setType(Property.CLOCKTIME_PROPERTY);
    if (d != null)
      currentProperty.setValue(d);
    else
      currentProperty.setValue("");
    addProperty(currentFullName, currentProperty);
  }

  public final void setBinaryProperty(Binary d) throws UserException {
    //System.out.println("setBinaryProperty() = " + d);
    currentProperty.setType(Property.BINARY_PROPERTY);
    if (d != null) {
      currentProperty.setValue(d);
    }
    else {
      currentProperty.setValue((Binary) null);
    }
    addProperty(currentFullName, currentProperty);
  }

  public final void setMoneyProperty(Money d) throws UserException {
   //System.out.println("setMoneyProperty() = " + d);
   currentProperty.setType(Property.MONEY_PROPERTY);
   if (d != null)
     currentProperty.setValue(d);
   else
     currentProperty.setValue("");
   addProperty(currentFullName, currentProperty);
 }


  public final void setDateProperty(Date d) throws UserException {
    //System.out.println("setDateProperty() = " + d);
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

  /**
   * Use this method to call another Navajo webservice.
   * If server is not specified, the Navajo server that is used to handle this request is also used to handle the new request.
   *
   * @param method
   * @throws UserException
   */
  public void setDoSend(String method) throws UserException, ConditionErrorException, SystemException, AuthorizationException {

    //System.err.println("IN NAVAJOMAP, SETDOSEND(), METHOD = " + method);
    try {
      username = (username == null) ? this.access.rpcUser : username;
      password = (password == null) ? this.access.rpcPwd : password;

      if (password == null)
        password = "";

      // Always copy globals.
      if ( inMessage.getMessage("__globals__") != null ) {
		  Message globals = inMessage.getMessage("__globals__").copy(outDoc);
		  try {
			outDoc.addMessage(globals);
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
      
      if (server != null) {
        NavajoClient nc = new NavajoClient();
        if (keyStore != null) {
          nc.setSecure(keyStore, keyPassword, true);
        }
        inDoc = nc.doSimpleSend(outDoc, server, method, username, password, -1, true);
      }
      else {
        Header h = outDoc.getHeader();
        if (h == null) {
          h = NavajoFactory.getInstance().createHeader(outDoc, method, username, password, -1);
          outDoc.addHeader(h);
        } else {
          h.setRPCName(method);
          h.setRPCPassword(password);
          h.setRPCUser(username);
          h.setExpirationInterval(-1);
          // Clear request id.
          h.setRequestId(null);
        }
        inDoc = access.getDispatcher().handle(outDoc, access.getUserCertificate());
      }

      Message error = inDoc.getMessage("error");
      if (error != null) {
          String errMsg = error.getProperty("message").getValue();
          String errCode = error.getProperty("code").getValue();
          throw new UserException(Integer.parseInt(errCode), errMsg);
      }

      boolean authenticationError = false;
      Message aaaError = inDoc.getMessage(AuthorizationException.AUTHENTICATION_ERROR_MESSAGE);
      if (aaaError == null) {
        aaaError = inDoc.getMessage(AuthorizationException.AUTHORIZATION_ERROR_MESSAGE);
      } else {
        authenticationError = true;
      }
      if (aaaError != null) {
        System.err.println("THROWING AUTHORIZATIONEXCEPTION IN NAVAJOMAP....");
        throw new AuthorizationException(authenticationError, !authenticationError,
                                         aaaError.getProperty("User").getValue(),
                                         aaaError.getProperty("Message").getValue());
      }

      if (breakOnConditionError && inDoc.getMessage("ConditionErrors") != null) {
    	  System.err.println("BREAKONCONDITIONERROR WAS SET TO TRUE, RETURNING CONDITION ERROR");
          throw new ConditionErrorException(inDoc);
      } else if (inDoc.getMessage("ConditionErrors") != null) {
    	  System.err.println("BREAKONCONDITIONERROR WAS SET TO FALSE, RETURNING....");
    	  return;
      }
      
      

      if (!compare.equals("")) {
        //isEqual = inMessage.isEqual(inDoc);

        Message other = inMessage.getMessage(compare);
        Message rec = inDoc.getMessage(compare);

        System.err.println("other = " + other);
        System.err.println("rec = " + rec);
        System.err.println("skipProperties = " + skipProperties);
        if (other == null || rec == null)
          isEqual = false;
        else
          isEqual = other.isEqual(rec, this.skipProperties);

        System.err.println("IN NAVAJOMAP(), ISEQUAL = " + isEqual);
      } else {
        outDoc = inDoc;
      }
//if (inDoc.getMessage("error") != null) {
      //    throw new UserException(-1, "ERROR while accessing webservice: " + method + ":: " + inDoc.getMessage("error").getProperty("message").getValue());
      //}
   } catch (com.dexels.navajo.client.ClientException e) {
      e.printStackTrace();
      throw new SystemException(-1, e.getMessage());
   } catch (FatalException fe) {
      fe.printStackTrace();
      throw new SystemException(-1, fe.getMessage());
   }
  }

  private Message getMessage(String fullName) throws UserException {
    Message msg = null;
    if (msgPointer != null)
      msg = msgPointer.getMessage(fullName);
    else
      msg = inDoc.getMessage(fullName);
    if (msg == null)
      throw new UserException(-1, "Message " + fullName + " does not exists in response document");
    return msg;
  }

  public Object getProperty(String fullName) throws Exception {
	   Property p = getPropertyObject(fullName);
	   return p.getTypedValue(); 
  }
  
  private Property getPropertyObject(String fullName) throws UserException {
    Property p = null;
    if (msgPointer != null) {
      p = msgPointer.getProperty(fullName);
    } else {
      p = inDoc.getProperty(fullName);
    }
    if (p == null)
      throw new UserException(-1, "Property " + fullName + " does not exists in response document");
    return p;
  }

   public boolean getBooleanProperty(String fullName) throws UserException {

    Property p = getPropertyObject(fullName);
    //System.err.println("in getBooleanProperty("+fullName+")");
    //System.err.println("VALUE = " + p.getValue());
    if (p.getType().equals(Property.BOOLEAN_PROPERTY) && !p.getValue().equals("")) {
        return p.getValue().equals("true");
    }
    else
        throw new UserException(-1, "Empty boolean property: " + fullName);

  }

  public final int getIntegerProperty(String fullName) throws UserException {

    Property p = getPropertyObject(fullName);
    if (p.getType().equals(Property.INTEGER_PROPERTY) && !p.getValue().equals(""))
        return Integer.parseInt(p.getValue());
    else
        throw new UserException(-1, "Empty integer property: " + fullName);

  }

   public final double getFloatProperty(String fullName) throws UserException {

    Property p = getPropertyObject(fullName);
    if (p.getType().equals(Property.FLOAT_PROPERTY) && !p.getValue().equals(""))
        return Double.parseDouble(p.getValue());
    else
        throw new UserException(-1, "Empty float property: " + fullName);

  }

   
  public final Binary getBinaryProperty(String fullName) throws UserException {
    Property p = getPropertyObject(fullName);
    if (!p.getType().equals(Property.BINARY_PROPERTY)) {
      throw new UserException(-1, "Property " + fullName + " not of type binary");
    }
    System.err.println("Returning Binary property: ");
    return (Binary) p.getTypedValue();
  }

  public final ClockTime getClockTimeProperty(String fullName) throws UserException {
   Property p = getPropertyObject(fullName);
   if (!p.getType().equals(Property.CLOCKTIME_PROPERTY)) {
     throw new UserException(-1, "Property " + fullName + " not of type clocktime");
   }
   System.err.println("Returning clocktime property: ");
   return (ClockTime) p.getTypedValue();
 }

 public final Money getMoneyProperty(String fullName) throws UserException {
  Property p = getPropertyObject(fullName);
  if (!p.getType().equals(Property.MONEY_PROPERTY)) {
    throw new UserException(-1, "Property " + fullName + " not of type money");
  }
  System.err.println("Returning money property: ");
  return (Money) p.getTypedValue();
}


  public final String getStringProperty(String fullName) throws UserException {

    Property p = getPropertyObject(fullName);
    return p.getValue();

  }
  
  
  /**
   * Determine whether a property or message object exists within the response document.
   * If messagePointer is set, search is relative from messagePointer.
   *
   * @param fullName
   * @return
   * @throws UserException
   */
  public final boolean getExists(String fullName) throws UserException {

    try {
      Property p = getPropertyObject(fullName);
      return true;
    } catch (Exception e) {
      try {
        Message msg = getMessage(fullName);
        return true;
      } catch (Exception e2) {
        return false;
      }
    }
  }

  public Date getDateProperty(String fullName) throws UserException {

    Property p = getPropertyObject(fullName);
    if (p.getType().equals(Property.DATE_PROPERTY)) {
        if (p.getValue() != null && !p.getValue().equals(""))
          return com.dexels.navajo.util.Util.getDate(p.getValue());
        else
          return null;
    }
    else
        throw new UserException(-1, "Invalid date property: " + fullName + "(string value = " + p.getValue() + ", type = " + p.getType() + " )");

  }

  /**
   * Set the messagePointer to an existin top level message in the current received Navajo document.
   * The following methods will use this messagePointer as an offset:
   * - getMessage()
   * - getMessages()
   * - getDateProperty()
   * - getExists()
   * - getStringProperty()
   * - getIntegerProperty()
   * - getBooleanProperty()
   * - getFloatProperty()
   * - getMoneyProperty()
   *
   * @param m
   * @throws UserException
   */
  public void setMessagePointer(String m) throws UserException {

    this.messagePointer = m;
    if (m.equals("")) {
      msgPointer = null;
      return;
    }
    msgPointer = (msgPointer == null ? inDoc.getMessage(messagePointer) : msgPointer.getMessage(messagePointer));

    //if (msgPointer == null)
    //   throw new UserException(-1, "Could not find message: " + messagePointer + " in response document");
  }

  public MessageMap getMessage() throws UserException {

      if (msgPointer == null)
        return null;
      MessageMap mm = new MessageMap();
      mm.setMsg(msgPointer);
      return mm;

  }

  /**
   * Try to return messages from using messagePointer, if no messages are found return null.
   *
   * @return
   * @throws UserException
   */
  public MessageMap [] getMessages() throws UserException {

    if (msgPointer == null)
        return null;
    if (!msgPointer.isArrayMessage())
        throw new UserException(-1, "getMessages can only be used for array messages");
    try {
      ArrayList all = msgPointer.getAllMessages(); //inDoc.getMessages(messagePointer);
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
      Message msg = MappingUtils.getMessageObject(currentFullName, null, false, outDoc, false, "", -1);
      String propName = p.getName();
      msg.addProperty(p);
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }

  }

  /**
   * Use sendThrough to send an entire current input message using the NavajoMap doSend method.
   *
   * @param b
   */
  public void setSendThrough(boolean b) throws UserException {

    try {
      ArrayList all = inMessage.getAllMessages();
      for (int i = 0; i < all.size(); i++) {
        Message m = inMessage.copyMessage( (Message) all.get(i), outDoc);
        outDoc.addMessage(m);
      }
    } catch (Exception e) {
       throw new UserException(-1, e.getMessage(), e);
    }

  }

  public boolean isExists() {
    return false;
  }

  public void setKeyPassword(String keyPassword) {
    this.keyPassword = keyPassword;
  }

  public void setKeyStore(String keyStore) {
    this.keyStore = keyStore;
  }

  public void setCompare(String message) {
    this.compare = message;
  }

  public void setSkipProperties(String list) {
    this.skipProperties = list;
    System.err.println("in setSkipProperties(): " + list);
  }

  public boolean getIsEqual() {
    return isEqual;
  }

  /**
   * If specified, the NavajoMap will break on a condition error and send this as a response.
   * @param breakOnConditionError
   */
  public void setBreakOnConditionError(boolean b) {
	System.err.println("IN setBreakOnConditionError(" + b + ")");
    this.breakOnConditionError = b;
  }

  /**
   * Set the outDoc to be the current outputDoc in the access object.
   *
   * @param useCurrentOutDoc
   */
  public void setUseCurrentOutDoc(boolean b) throws NavajoException {
	  if (b) {
		  this.useCurrentOutDoc = b;
		  this.outDoc = access.getOutputDoc();
		  // Copy param messages.
		  if ( inMessage.getMessage("__parms__") != null ) {
			  Message params = inMessage.getMessage("__parms__").copy(outDoc);
			  outDoc.addMessage(params);
		  }
	  }
  }
}
