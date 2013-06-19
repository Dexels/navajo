package com.dexels.navajo.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.navajomap.MessageMap;
import com.dexels.navajo.adapter.navajomap.manager.NavajoMapManager;
import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.AsyncClient;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.client.async.NavajoClientResourceManager;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.resource.ResourceManager;
import com.dexels.navajo.util.AuditLog;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class NavajoMap extends AsyncMappable implements Mappable, HasDependentResources, TmlRunnable, NavajoResponseHandler {

  public String doSend;
  public Binary navajo;
  public String username = null;
  public String password = null;
  public String server = null;
  // For scheduling tasks from NavajoMap.
  public String trigger = null;
  public String taskId = null;

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
  // Property id is used to set 'properties' like direction, show, suppress of a specified property.
  public String propertyId;
  public MessageMap message;
  public MessageMap [] messages;
  public OptionMap [] selections;
 
  public String messagePointer;
  public String selectionPointer = null;

  public boolean exists;
  public String append;
  public String appendTo;
  // appendParms is used to append entire output doc of called webservice to param block.
  public String appendParms;
  public boolean sendThrough;
  /**
   * if useCurrentOutDoc is set, the NavajoMap will use the outDoc from the access object instead of creating a new one.
   */
  public boolean useCurrentOutDoc;
  public boolean breakOnConditionError = true;
  public boolean breakOnException = true;
  public String keyStore;
  public String keyPassword;
  public String compare = "";
  public String skipProperties = "";
  public boolean isEqual = false;
  public boolean performOrderBy = false;
  public String suppressProperties = null;
  public String showProperties = null;
  public String inputProperties = null;
  public String outputProperties = null;

  protected Navajo inDoc;
  protected Navajo outDoc;
  //private NavajoClient nc;
  private Property currentProperty;
  private String currentFullName;
  protected Access access;
  
  private RequestQueue myRequestQueue;
  private final Map<String,Object> attributes = new HashMap<String,Object>();

  protected NavajoConfigInterface config;
  protected Navajo inMessage;
  protected Message msgPointer;

  public String method;
  
  // If block is set, the web service calls blocks until a result is received.
  // Default value is TRUE.
  public boolean block = true;
  protected boolean serviceCalled = false;
  protected boolean serviceFinished = false;
  private Exception myException = null;
  
  private NavajoMapResponseListener myResponseListener = null;
  private String id;
  
  private List<String> deletedProperties = new ArrayList<String>();
  private List<String> deletedMessages = new ArrayList<String>();
  
  public String getId() {
	  return id;
  }

  public void setId(String id) {
	  this.id = id;
  }

public void setMyResponseListener(NavajoMapResponseListener myResponseListener) {
	  this.myResponseListener = myResponseListener;
  }

private final static Logger logger = LoggerFactory.getLogger(NavajoMap.class);
private static final long MAX_WAITTIME = 300000;

  public boolean isBlock() {
	  return block;
  }

  public void setBlock(boolean block) {
	  this.block = block;
  }

private Object waitForResult = new Object();
private String resource;
  
  public void load(Access access) throws MappableException, UserException {
    this.access = access;
    this.config = DispatcherFactory.getInstance().getNavajoConfig();
    this.inMessage = access.getInDoc();
    killOnFinnish = true;
    try {
      outDoc = NavajoFactory.getInstance().createNavajo();
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }

  public void store() throws MappableException, UserException {
	  if ( block && serviceCalled ) {
		  waitForResult();
	  }
  }

  public void setOutDoc(Navajo n) {
	  this.outDoc = n;
  }
  
  /**
   * Set this to the message path to which the result of the called service needs to be appended.
   * Always used in conjunction with setAppend().
   * 
   * @param messageOffset
   * @throws UserException
   */
  public final void setAppendTo(String messageOffset) throws UserException {
	  appendTo = messageOffset;
  }
  
  private final synchronized void waitForResult() throws UserException {
	  if (!serviceCalled) {
		  throw new UserException(-1, "Call webservice before retrieving result.");
	  }
	  if ( !serviceFinished ) {
		  // Wait for result.
		  //
		  //
		  synchronized (waitForResult) {
			  try {
				  waitForResult.wait(MAX_WAITTIME);
			  } catch (InterruptedException e) {
				  logger.error("Error: ", e);
			  }
		  }
	  }
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

	  waitForResult();

	  if (messageOffset.equals("")) {
		  access.setOutputDoc(inDoc);
		  return;
	  }

	  try {
		  Navajo currentDoc = access.getOutputDoc();
		  Message currentMsg = access.getCurrentOutMessage();
		  List<Message> list = null;
		  // If append message equals '/'.
		  if ( messageOffset.equals(Navajo.MESSAGE_SEPARATOR) ) {
			  list = inDoc.getAllMessages();
		  } else if ( inDoc.getMessage(messageOffset) == null ) {
			  return;
		  } else if ( !inDoc.getMessage(messageOffset).getType().equals(Message.MSG_TYPE_ARRAY) ) {
			  list = new ArrayList<Message>();
			  list.add( inDoc.getMessage(messageOffset) );
		  } else { // For array messages...
			  list = new ArrayList<Message>();
			  list.add(inDoc.getMessage(messageOffset));
			  //list = inDoc.getMessages(messageOffset);
		  }

		  /**
		   * appendTo logic. If appendTo ends with '/' append the entire append message to the defined appendTo message.
		   * If appendTo does not end with '/', merge the append message with the defined appendTo message.
		   */
		  boolean appendToComplete = ( appendTo != null && !appendTo.equals(Navajo.MESSAGE_SEPARATOR) && appendTo.endsWith(Navajo.MESSAGE_SEPARATOR) );
		  if ( appendToComplete ) {
			  // Strip last "/".
			  appendTo = appendTo.substring(0, appendTo.length() - 1);
		  }

		  // Check whether incoming array message needs to be expanded: if not appendToComplete and if appendTo is defined and
		  // appendTo is array message.

		  boolean appendToIsArray = false;
		  if ( appendTo != null && currentMsg != null &&   currentMsg.getMessage(appendTo) != null && currentMsg.getMessage(appendTo).getType().equals(Message.MSG_TYPE_ARRAY )) {
			  appendToIsArray = true;
		  }

		  if ( appendTo != null && currentDoc.getMessage(appendTo) != null && currentDoc.getMessage(appendTo).getType().equals(Message.MSG_TYPE_ARRAY) ) {
			  appendToIsArray = true;
		  }
		  if ( !appendToComplete && appendTo != null && 
				  list != null && 
				  list.get(0) != null && 
				  list.get(0).getType().equals(Message.MSG_TYPE_ARRAY) &&
				  appendToIsArray
		  ) {
			  // Expand list if it contains an array message.
			  list = list.get(0).getAllMessages();
		  }
		  if(list==null) {
			  logger.warn("Can not append: appendTo target can not be found");
			  return;
		  }
		  for (int i = 0; i < list.size(); i++) {
			  Message inMsg = list.get(i);
			  // Clone message and append it to currentMsg if it exists, else directly under currentDoc.
			  //currentDoc.importMessage(inMsg);
			  Message clone = inDoc.copyMessage(inMsg, currentDoc);
			  if (currentMsg != null) {
				  if ( appendTo != null ) {
					  if ( appendTo.equals(Navajo.MESSAGE_SEPARATOR)) {
						  /**
						   * NOTE 21/3/2013 (AS): USING ADDMESSAGE USING OVERWRITE FLAG
						   * WILL CAUSE CURRENTMSG TO BE OVERWRITTEN IFF CLONE MSG NAME IS THE SAME!!!
						   * THIS WILL CREATE A "DANGLING" MSG POINTER RENDERING ALL FURTHER
						   * MESSAGE ADDITIONS USELESS (THEY WILL NOT APPEAR)
						   * 
						   * THEREFORE: CHECK WHETHER NAME OF CURRENT OUT MESSAGE POINTER EQUALS
						   * THAT OF CLONE MESSAGE.
						   */
						  
						  if ( clone.getName().equals(currentMsg.getName() ) ) {
							  currentMsg.merge(clone);
						  } else {
							  currentDoc.addMessage(clone, true);
						  }
					  } else if ( currentMsg.getMessage(appendTo) != null ) {
						  if ( currentMsg.getMessage(appendTo).getType().equals(Message.MSG_TYPE_ARRAY )  ) { // For array messages do not overwrite.
							  currentMsg.getMessage(appendTo).addMessage(clone);
						  } else {
							  if ( appendToComplete ) {
								  currentMsg.getMessage(appendTo).addMessage(clone);
							  } else {
								  currentMsg.getMessage(appendTo).merge(clone);
							  }
						  }
					  } else {
						  throw new UserException(-1, "Unknown appendTo message: " + appendTo);
					  }
				  } else {
					  // Merge message with existing message.
					  if ( clone.getName().equals(currentMsg.getName()) ) {
						  currentMsg.merge(clone);
					  } else {
						  currentMsg.addMessage(clone, true);
					  }
				  }
			  } else {
				  if ( appendTo != null ) {
					  if ( appendTo.equals(Navajo.MESSAGE_SEPARATOR)) {
						  currentDoc.addMessage(clone, true);
					  } else if ( currentDoc.getMessage(appendTo) != null ) {
						  if ( currentDoc.getMessage(appendTo).getType().equals(Message.MSG_TYPE_ARRAY )  ) { // For array messages do not overwrite.
							  currentDoc.getMessage(appendTo).addMessage(clone);
						  } else {
							  if ( appendToComplete ) {
								  currentDoc.getMessage(appendTo).addMessage(clone);
							  } else {
								  currentDoc.getMessage(appendTo).merge(clone);
							  }
						  }
					  } else {
						  throw new UserException(-1, "Unknown appendTo message: " + appendTo);
					  }
				  } else {
					  // Check if message already exists, if so, merge it with existing message.
					  if ( currentDoc.getMessage(clone.getName()) != null ) {
						  currentDoc.getMessage(clone.getName()).merge(clone);
					  } else {
						  currentDoc.addMessage(clone, true);
					  }
				  }
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

	  waitForResult();

	  try {
		  Message parm = ( access.getCompiledScript().currentParamMsg == null ? 
				  access.getInDoc().getMessage("__parms__") :
					  access.getCompiledScript().currentParamMsg);

		  List<Message> list = null;
		  // If append message equals '/'.
		  if ( messageOffset.equals(Navajo.MESSAGE_SEPARATOR) ) {
			  list = inDoc.getAllMessages();
		  } else if ( inDoc.getMessage(messageOffset) == null ) {
			  return;
		  } else if ( inDoc.getMessage(messageOffset).getType().equals(Message.MSG_TYPE_ARRAY) ) {
			  list = new ArrayList<Message>();
			  list.add( inDoc.getMessage(messageOffset) );
		  } else {
			  list = inDoc.getMessages(messageOffset);
		  }

		  for (int i = 0; i < list.size(); i++) {
			  Message inMsg = list.get(i);
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
    	e.printStackTrace(Access.getConsoleWriter(access));
    	throw new UserException(-1, e.getMessage());
    }
  }

  public final void setDeleteProperty(String fullName) throws UserException {
	  setPropertyName(fullName);
	  if ( currentProperty != null ) {
		  Message m = currentProperty.getParentMessage();
		  deletedProperties.add(fullName);
		  if ( m != null ) {
			  m.removeProperty(currentProperty);
		  }
	  }
  }
  
  public final void setDeleteMessage(String fullName) {
	  Message m = outDoc.getMessage(fullName);
	  if ( m != null ) {
			 outDoc.removeMessage(m);
	  }
	  deletedMessages.add(fullName);
  }
  
  public final void setIntegerProperty(int i) throws UserException {
     //System.out.println("in setIntegerProperty() : i = " + i);
     currentProperty.setType(Property.INTEGER_PROPERTY);
     currentProperty.setValue(i+"");
     addProperty(currentProperty);
  }

  public final void setFloatProperty(double i) throws UserException {
     //System.out.println("in setFloatProperty() : i = " + i);
     currentProperty.setType(Property.FLOAT_PROPERTY);
     currentProperty.setValue(i+"");
     addProperty(currentProperty);
  }

  public final void setStringProperty(String s) throws UserException {
    currentProperty.setType(Property.STRING_PROPERTY);
    currentProperty.setValue(s);
    addProperty(currentProperty);
  }
  
  public final void setProperty(Object o) throws UserException {
	  if ( o == null ) {
		  currentProperty.setValue((String) null);
	  } else {
		  currentProperty.setAnyValue(o);
	  }
	  addProperty(currentProperty);
  }
  
  public final void setPropertyType(String type) throws UserException {
	  if ( currentProperty == null ) {
		  throw new UserException(-1, "Set property name first");
	  }
	  currentProperty.setType(type);
  }
  
  public final void setBooleanProperty(boolean b) throws UserException {
    currentProperty.setType(Property.BOOLEAN_PROPERTY);
    currentProperty.setValue(b);
    addProperty(currentProperty);
  }

  public final void setClockTimeProperty(ClockTime d) throws UserException {
    //System.out.println("setClockTimeProperty() = " + d);
    currentProperty.setType(Property.CLOCKTIME_PROPERTY);
    if (d != null)
      currentProperty.setValue(d);
    else
      currentProperty.setValue("");
    addProperty(currentProperty);
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
    addProperty(currentProperty);
  }

  public final void setMoneyProperty(Money d) throws UserException {
   //System.out.println("setMoneyProperty() = " + d);
   currentProperty.setType(Property.MONEY_PROPERTY);
   if (d != null)
     currentProperty.setValue(d);
   else
     currentProperty.setValue("");
   addProperty(currentProperty);
 }


  public final void setDateProperty(Date d) throws UserException {
    //System.out.println("setDateProperty() = " + d);
    currentProperty.setType(Property.DATE_PROPERTY);
    if (d != null)
      currentProperty.setValue(d);
    else
      currentProperty.setValue("");
    addProperty(currentProperty);
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
   * Set a Navajo object directly.
   * 
   * @param b
   * @throws UserException
   */
  public void setNavajo(Binary b) throws UserException {
	  try {
	  InputStream is = b.getDataAsStream();
	  inDoc = NavajoFactory.getInstance().createNavajo(is);
	  is.close();
	  } catch (Throwable t) {
		  throw new UserException(-1, t.getMessage(), t);
	  }
  }

  /**
   * Gets the Navajo object.
   * If inDoc is present return inDoc, else return inMessage (request Navajo).
   * 
   * @return
   * @throws UserException
   */
  public Binary getNavajo() throws UserException {
	  
	  
	  Binary b = new Binary();
	  OutputStream is = b.getOutputStream();
	  try {
		  if ( inDoc != null) {
			  inDoc.write(is);
		  } else {
			  inMessage.write(is);
		  }
		  is.close();
	  } catch (Exception e) {
		  throw new UserException(-1, e.getMessage());
	  }
	  return b;
  }
  
  protected void prepareOutDoc() {
	  // If currentOutDoc flag was set, make sure to copy outdoc.
	  if ( this.useCurrentOutDoc ) {
		  if ( this.outDoc != null ) {
			  /**
			   * NOTE: THIS MERGE OPERATION WILL CAUSE EXISTING PROPERTIES IN outDoc TO
			   * BE OVERWRITTEN WITH THE SAME PROPERTIES IN access.getOutputDoc().
			   * THIS IS NOT EXPECTED BEHAVIOR.
			   */
			  this.outDoc.merge(access.getOutputDoc().copy(), true) ;
		  } else {
			  this.outDoc = access.getOutputDoc().copy();
		  }

		  // Copy param messages.
		  if ( inMessage.getMessage("__parms__") != null ) {
			  Message params = inMessage.getMessage("__parms__").copy(outDoc);
			  try {
				  outDoc.addMessage(params);
			  } catch (NavajoException e) {
				  e.printStackTrace(Access.getConsoleWriter(access));
			  }
		  }

		  // Check for deleted messages.
		  if ( deletedMessages.size() > 0 ) {
			  for (String dMn: deletedMessages ) {
				  Message dM = outDoc.getMessage(dMn);
				  if ( dM != null ) {
					  Navajo rN = dM.getRootDoc();
					  rN.removeMessage(dMn);
				  }
			  }
		  }
		  // Check for deleted properties.
		  if ( deletedProperties.size() > 0 ) {
			  for (String dPn: deletedProperties) {
				  if ( dPn != null ) {
					  Property dP = outDoc.getProperty(dPn);
					  if ( dP != null ) {
						  Message dm = dP.getParentMessage();
						  dm.removeProperty(dP);
					  }
				  }
			  }
		  }
	  }

	  // Always copy globals.
	  if ( inMessage.getMessage("__globals__") != null ) {
		  Message globals = inMessage.getMessage("__globals__").copy(outDoc);
		  try {
			  outDoc.addMessage(globals);
		  } catch (NavajoException e) {
			  e.printStackTrace(Access.getConsoleWriter(access));
		  }
	  }
  }
  
  /**
   * Use this method to call another Navajo webservice.
   * If server is not specified, the Navajo server that is used to handle this request is also used to handle the new request.
   *
   * @param method
   * @throws UserException
   */
  public void setDoSend(String method) throws UserException, ConditionErrorException, SystemException {
  
	  // Reset current msgPointer when performing new doSend.
	  msgPointer = null;
	  setMethod(method);

	  this.username = (username == null) ? this.access.rpcUser : username;
	  this.password = (password == null) ? this.access.rpcPwd : password;
	  this.method = method;
	  
	  if (password == null)
		  password = "";

	  prepareOutDoc();
	  
	  try {
		  if(this.resource!=null) {
			  serviceCalled = true;
			  AsyncClient ac = NavajoClientResourceManager.getInstance().getAsyncClient(this.resource);
			  ac.callService(outDoc, method);
		  } else 
			  if (server != null) { // External request.
				  try {
					  ManualAsyncClient ac = AsyncClientFactory.getManualInstance();
					  ac.callService(server.startsWith("http") ? server : "http://" + server, username, password, outDoc, method, this);
				  } catch (Exception e) {
					  throw new UserException(-1, e.getMessage(), e);
				  }

				  serviceCalled = true;
			  } // Internal request.
			  else {
				  try {
					  inDoc = null;
					  isFinished = false;
					  serviceFinished = false;
					  if ( block ) {
						  this.run();
					  } else {
						  SchedulerRegistry.getScheduler().submit(this, true);
					  }
					  serviceCalled = true;
					  if ( getException() != null ) {
						  if ( getException() instanceof ConditionErrorException ) {
							  throw (ConditionErrorException) getException();
						  } else if ( getException() instanceof UserException ) {
							  throw (UserException) getException();
						  } else if ( getException() instanceof SystemException ) {
							  throw (SystemException) getException();
						  } else {
							  throw new SystemException(-1, "", getException());
						  }
					  }
				  } catch (IOException e) {
					  throw new SystemException("Error submitting to remote server:",e);

				  }
			  }

	  } catch (NavajoException e) {
		  throw new SystemException("Error connecting to remote server",e);
	  } catch (IOException e) {
		  throw new SystemException("Error connecting to remote server",e);
	  } 

  }

  public Message getMessage(String fullName) throws UserException {
	waitForResult();
	Message msg = null;
    if (msgPointer != null)
      msg = msgPointer.getMessage(fullName);
    else
      msg = inDoc.getMessage(fullName);
    if (msg == null)
      throw new UserException(-1, "Message " + fullName + " does not exists in response document");
    return msg;
  }

  public final Object getProperty(String fullName) throws Exception {

	  Property p = getPropertyObject(fullName);

	  if ( p.getType().equals(Property.SELECTION_PROPERTY) ) {
		  if ( p.getSelected() != null ) {
			  return p.getSelected().getValue();
		  } else {
			  return null;
		  }
	  }
	  return p.getTypedValue();
  }
  
  public void setSelectionPointer(String selectionPointer) {
	  this.selectionPointer = selectionPointer;
  }
  
  public void setSelections(OptionMap [] selections) throws Exception {

	  if ( currentProperty == null ) {
		  throw new UserException(-1, "Set property name first.");
	  }
	 
	  currentProperty.setType(Property.SELECTION_PROPERTY);
	  
	  currentProperty.clearValue();
	  currentProperty.clearSelections();
	  
	  int selected = 0;
	  for (int i = 0; i < selections.length; i++) {
		  Selection s = NavajoFactory.getInstance().createSelection(currentProperty.getRootDoc(), 
				  selections[i].getOptionName(), selections[i].getOptionValue(), selections[i].getOptionSelected());
		  currentProperty.addSelection(s);
		  if ( selections[i].getOptionSelected() ) {
			  selected++;
		  }
	  }	   
	  
	  if ( selected > 1 ) {
		  currentProperty.setCardinality("+");
	  } else {
		  currentProperty.setCardinality("1");
		  
	  }
	  addProperty(currentProperty);

  }
  
  public OptionMap [] getSelections() throws Exception {
	  
	    if ( selectionPointer == null ) {
	    	throw new UserException(-1, "Set selectionPointer first.");
	    }
	    Property p = getPropertyObject(selectionPointer);
	    if ( !p.getType().equals(Property.SELECTION_PROPERTY) ) {
	    	throw new UserException(-1, "selections only supported for selection properties");
	    }
	    
	    ArrayList<Selection> all = p.getAllSelections();
	    OptionMap [] om = new OptionMap[all.size()];
	    for ( int i = 0; i < all.size(); i++ ) {
	    	Selection s = all.get(i);
	    	om[i] = new OptionMap();
	    	om[i].setOptionName(s.getName());
	    	om[i].setOptionValue(s.getValue());
	    	om[i].setOptionSelected(s.isSelected());
	    	
	    }
	    
		return om;
	}
  
  public Property getPropertyObject(String fullName) throws UserException {
	  waitForResult();

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
    return (Binary) p.getTypedValue();
  }

  public final ClockTime getClockTimeProperty(String fullName) throws UserException {
   Property p = getPropertyObject(fullName);
   if (!p.getType().equals(Property.CLOCKTIME_PROPERTY)) {
     throw new UserException(-1, "Property " + fullName + " not of type clocktime");
   }
   return (ClockTime) p.getTypedValue();
 }

 public final Money getMoneyProperty(String fullName) throws UserException {
  Property p = getPropertyObject(fullName);
  if (!p.getType().equals(Property.MONEY_PROPERTY)) {
    throw new UserException(-1, "Property " + fullName + " not of type money");
  }
  return (Money) p.getTypedValue();
}


 public final String getStringProperty(String fullName) throws Exception {

	 Property p = getPropertyObject(fullName);
	 if ( p.getType().equals(Property.SELECTION_PROPERTY )) {
		 if ( p.getSelected() != null ) {
			 return p.getSelected().getValue();
		 } else {
			 return null;
		 }
	 } else {
		 return p.getValue();
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
  public final boolean getExists(String fullName) throws UserException {

    try {
      getPropertyObject(fullName);
      return true;
    } catch (Exception e) {
      try {
        getMessage(fullName);
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
          return (Date) p.getTypedValue();
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

	  waitForResult();

	  this.messagePointer = m;
	  if (m.equals("")) {
		  msgPointer = null;
		  return;
	  }
	  msgPointer = (msgPointer == null ? inDoc.getMessage(messagePointer) : msgPointer.getMessage(messagePointer));

  }

  public MessageMap getMessage() throws UserException {

	  waitForResult();
		 
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

	  waitForResult();

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

  private void addProperty(Property p) throws UserException {

    try {
      Message msg = MappingUtils.getMessageObject(currentFullName, null, false, outDoc, false, "", -1);
      if ( msg == null ) {
    	  throw new UserException(-1, "Could not create property " + currentFullName + ". Perhaps a missing message name?");
      }
      msg.addProperty(p);
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage(),e);
    }

  }

  /**
   * Use sendThrough to send an entire current input message using the NavajoMap doSend method.
   *
   * @param b
   */
  public void setSendThrough(boolean b) throws UserException {

    try {
      ArrayList<Message> all = inMessage.getAllMessages();
      for (int i = 0; i < all.size(); i++) {
        Message m = inMessage.copyMessage( all.get(i), outDoc);
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
  }

  public boolean getIsEqual() {
    return isEqual;
  }

  /**
   * If specified, the NavajoMap will break on a condition error and send this as a response.
   * @param breakOnConditionError
   */
  public void setBreakOnConditionError(boolean b) {
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
	  }
  }
  
  public void setMethod(String name) {
	 this.method = name;
  }
  
  public void setPerformOrderBy(boolean b) {
	  performOrderBy = b;
  }

  public void afterRequest() throws UserException {
	  if (method == null)
		  throw new UserException(-1, "AsyncProxyMap: specify a method");
  }

  public void afterResponse() {
	  access.setOutputDoc(inDoc);
  }

  public int getPercReady() {
	  return 0;
  }

  public void beforeResponse(Access access) {
	  access.setOutputDoc(inDoc);
  }

  public void continueAfterRun() throws UserException, ConditionErrorException, AuthorizationException {
	 try {
		  // Get task if if trigger was specified.
	      if ( trigger != null ) {
	    	  taskId = inDoc.getHeader().getSchedule();
	    	  logger.info("************************************************* TASKID: " + taskId);
	      }
	      
	      // Call sorted.
	      if ( performOrderBy ) {
	    	  OutputStream os = new OutputStream(){
	    		  public void write(int b) throws IOException {
	    			  // do nothing
	    		  } 
	    	  };
	    	  try {
	    		  inDoc.write(os);
	    	  } catch (NavajoException e) {
	    	  }
	      }
	      
	      Message error = inDoc.getMessage("error");
	      if (error != null && breakOnException ) {
	          String errMsg = error.getProperty("message").getValue();
	          String errCode = error.getProperty("code").getValue();
	          int errorCode = -1;
		          try {
					errorCode = Integer.parseInt(errCode);
				} catch (NumberFormatException e) {
					
					e.printStackTrace(Access.getConsoleWriter(access));
				}
	          throw new UserException(errorCode, errMsg);
	      } else if ( error != null ) {
	    	  AuditLog.log("NavajoMap", "EXCEPTIONERROR OCCURED, BUT WAS EXCEPTION HANDLING WAS SET TO FALSE, RETURNING....", Level.INFO, access.accessID);
	    	  return;
	      }

	      boolean authenticationError = false;
	      Message aaaError = inDoc.getMessage(AuthorizationException.AUTHENTICATION_ERROR_MESSAGE);
	      if (aaaError == null) {
	        aaaError = inDoc.getMessage(AuthorizationException.AUTHORIZATION_ERROR_MESSAGE);
	      } else {
	        authenticationError = true;
	      }
	      if (aaaError != null) {
	    	AuditLog.log("NavajoMap", "THROWING AUTHORIZATIONEXCEPTION IN NAVAJOMAP" + aaaError.getProperty("User").getValue(), Level.WARNING, access.accessID);
	        throw new AuthorizationException(authenticationError, !authenticationError,
	                                         aaaError.getProperty("User").getValue(),
	                                         aaaError.getProperty("Message").getValue());
	      }

	      if (breakOnConditionError && inDoc.getMessage("ConditionErrors") != null) {
	    	  String scriptName = "UNKNOWN";
	    	  if (access.getMyScript() != null) {
	    		  scriptName = access.getMyScript().getScriptName();
	    	  }
	    	  AuditLog.log("NavajoMap", ">>>> BREAKONCONDITIONERROR WAS SET TO TRUE, RETURNING CONDITION ERROR (" + scriptName + ")", Level.INFO, access.accessID);
	          throw new ConditionErrorException(inDoc);
	      } else if (inDoc.getMessage("ConditionErrors") != null) {
	    	  AuditLog.log("NavajoMap", "BREAKONCONDITIONERROR WAS SET TO FALSE, RETURNING....", Level.INFO, access.accessID);
	    	  return;
	      }
	      
	      // Set property directions.
	      processPropertyDirections(inDoc);
	      // Suppress properties.
	      processSuppressedProperties(inDoc);
	      // Show properties.
	      processShowProperties(inDoc);
	    
	      // Reset property directives
	      this.suppressProperties = null;
	      this.inputProperties = null;
	      this.outputProperties = null;
	      this.showProperties = null;
	      
	      if (!compare.equals("")) {
	        Message other = inMessage.getMessage(compare);
	        Message rec = inDoc.getMessage(compare);
			if (other == null || rec == null) {
				isEqual = false;
			} else {
				isEqual = other.isEqual(rec, this.skipProperties);
			}
	      } else {
	        outDoc = inDoc;
	      }
		  
	  } finally {
		   synchronized (waitForResult) {
				  waitForResult.notify();
			  }
			  if ( myResponseListener != null ) {
				  myResponseListener.onNavajoResponse(this);
			  }
	  }  
	 
  }
  
  public void run()  {
  
	  try {
		  Header h = outDoc.getHeader();
		  if (h == null) {
			  h = NavajoFactory.getInstance().createHeader(outDoc, method, username, password, -1);
			  outDoc.addHeader(h);
		  } else {
			  h.setRPCName(method);
			  h.setRPCPassword(password);
			  h.setRPCUser(username);
		  }
		  // Clear request id.
		  h.setRequestId(null);
		  h.setHeaderAttribute("parentaccessid", access.accessID);

		  inDoc = DispatcherFactory.getInstance().handle(outDoc, true);
		  serviceFinished = true;
		  serviceCalled = true;
		  continueAfterRun();
		  
	 } catch (Exception e) {
		 setException(e);
	  } finally {
		  setIsFinished();
	  }

  }

  public void setTrigger(String trigger) {
	  this.trigger = trigger;
  }

  public String getTaskId() {
	  return taskId;
  }
  
  /**
 * @param t  
 */
public void setTaskId(String t) {
//	  taskId = t;
//	  // Get response from TaskRunnerMap.
//	  TaskRunnerMap trm = new TaskRunnerMap();
//	  trm.setId(taskId);
//	  TaskMap tm = null; 
//	  while ( tm == null ) {
//		  tm = trm.getFinishedTask();
//		  if ( tm == null) {
//			  //logger.info("Waiting for task to finish....");
//			  try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//			}
//		  }
//	  }
//	  inDoc = tm.getMyTask().getResponse();
	  throw new IllegalStateException("Sorry! I broke setTaskId");
  }
  
  private final void processPropertyDirections(Navajo n) {
	  Iterator<Message> list;
	  try {
		  list = n.getAllMessages().iterator();
		  while ( list.hasNext() ) {
			  processPropertyDirections(list.next());
		  }
	  } catch (NavajoException e) {
	  }
  }
  
  private final void processShowProperties(Navajo n) {
	  if  ( showProperties == null ) {
		  return;
	  }
	  Iterator<Message> list;
	  try {
		  list = n.getAllMessages().iterator();
		  while ( list.hasNext() ) {
			  processShowProperties(list.next());
		  }
	  } catch (NavajoException e) {
	  }
  }
  
  private final void processShowProperties(Message m) {
	  Iterator<Property> allProps = new ArrayList<Property>(m.getAllProperties()).iterator();
	  while ( allProps.hasNext() ) {
		  Property p = allProps.next();
		  if ( !isPropertyInList(p, this.showProperties, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) ) {
			  m.removeProperty(p);
		  }
	  }
	  Iterator<Message> subMessages = m.getAllMessages().iterator();
	  while ( subMessages.hasNext() ) {
		  processShowProperties(subMessages.next());
		 }
  }
  
  private final void processSuppressedProperties(Navajo n) {
	  if  ( suppressProperties == null ) {
		  return;
	  }
	  Iterator<Message> list;
	  try {
		  list = n.getAllMessages().iterator();
		  while ( list.hasNext() ) {
			  processSuppressedProperties(list.next());
		  }
	  } catch (NavajoException e) {
	  }
	
  }
  
  private final void processPropertyDirections(Message m) {
	  Iterator<Property> allProps = m.getAllProperties().iterator();
	  while ( allProps.hasNext() ) {
		  Property p = allProps.next();
		  if ( isPropertyInList(p, this.outputProperties, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) ) {
			  p.setDirection(Property.DIR_OUT);
		  } else
			  if ( isPropertyInList(p, this.inputProperties, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) ) {
				  p.setDirection(Property.DIR_IN);
			  }
	  }
	  Iterator<Message> subMessages = m.getAllMessages().iterator();
	  while ( subMessages.hasNext() ) {
		  processPropertyDirections(subMessages.next());
	  }
  }
  
  private final void processSuppressedProperties(Message m) {
	  Iterator<Property> allProps = new ArrayList<Property>(m.getAllProperties()).iterator();
	  while ( allProps.hasNext() ) {
		  Property p = allProps.next();
		  if ( isPropertyInList(p, this.suppressProperties, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) ) {
			  m.removeProperty(p);
		  }
	  }
	  Iterator<Message> subMessages = m.getAllMessages().iterator();
	  while ( subMessages.hasNext() ) {
		  processSuppressedProperties(subMessages.next());
	  }
  }
  
  private final boolean isPropertyInList(Property prop, String propertyStringList, boolean isArrayMessageElement) {
	  if ( propertyStringList == null ) {
		  return false;
	  }
	  String [] propertyList = propertyStringList.split(";");
	  for (int i = 0; i < propertyList.length; i++) {
		  try {
			  if ( !isArrayMessageElement && propertyList[i].equals(prop.getFullPropertyName()) ) {
				  return true;
			  } else if ( isArrayMessageElement && propertyList[i].equals(prop.getName())) {
				  return true;
			  }
		  } catch (NavajoException e) {
		  }
	  }
	  return false;
  }
  
  public DependentResource[] getDependentResourceFields() {
	  return new DependentResource[]{new GenericDependentResource(GenericDependentResource.SERVICE_DEPENDENCY, "doSend", AdapterFieldDependency.class), 
			                         new GenericDependentResource("navajoserver", "server", AdapterFieldDependency.class)};
  }

  public static ResourceManager getResourceManager(String resourceType) {
	  if ( resourceType.equals("navajoserver") ) {
		  return NavajoMapManager.getInstance();
	  }
	  return null;
  }
  
  public String getOutputProperties() {
	  return outputProperties;
  }

  public void setOutputProperties(String outputProperties) {
	  this.outputProperties = outputProperties;
  }

  public void setSuppressProperties(String suppressProperties) {
	  this.suppressProperties = suppressProperties;
  }

  public void setInputProperties(String inputProperties) {
	  this.inputProperties = inputProperties;
  }

  public void setShowProperties(String showProperties) {
	  this.showProperties = showProperties;
  }
  
  private final String setProperPropertyDirective(String list, String propertyName) {
	  if (  list == null ) {
		  list = propertyName;
	  } else {
		  list = list + ";" + propertyName;
	  }
	  return list;
  }
  
  /**
   * Helper setter used by setPropertyDirective.
   * 
   * @param s
   */
  public void setPropertyId(String s) {
	  this.propertyId = s;
  }
  
  /**
   * Sets the directive of a property specified by propertyId.
   * Supported directives: suppress, show, in, out.
   * 
   * @param directive
   */
  public void setPropertyDirective(String directive) {
	  if ( directive.equalsIgnoreCase("suppress") ) {
		 this.suppressProperties = setProperPropertyDirective(this.suppressProperties, this.propertyId);
	  } else if ( directive.equalsIgnoreCase("show") ) {
		 this.showProperties = setProperPropertyDirective(this.showProperties, this.propertyId);
	  } else if ( directive.equalsIgnoreCase("in") ) {
		  this.inputProperties = setProperPropertyDirective(this.inputProperties, this.propertyId);
	  } else if ( directive.equalsIgnoreCase("out") ) {
		  this.outputProperties = setProperPropertyDirective(this.outputProperties, this.propertyId);
	  }
  }

  public void setBreakOnException(boolean breakOnException) {
	  this.breakOnException = breakOnException;
  }

  /**
   * This is a callback method for the async Navajo Client.
   * 
 * @param response  
 */
  public void onResponse(Navajo response) {
	  inDoc = response;
	  serviceFinished = true;
	  serviceCalled = true;
	  try {
		continueAfterRun();
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
	} 
  }

  /**
   * This is a callback method for the async Navajo Client.
   */
  @Override
  public void onFail(Throwable t) throws IOException {
	  Navajo response = null;
	  try {
		  response = NavajoFactory.getInstance().createNavajo();
		  Message error = NavajoFactory.getInstance().createMessage(response, "error");
		  response.addMessage(error);
		  Property msg = NavajoFactory.getInstance().createProperty(response, "message", Property.STRING_PROPERTY,
				  ( t != null ? t.getMessage() : "unknown" ), 0, "", "");
		  Property code = NavajoFactory.getInstance().createProperty(response, "code", Property.STRING_PROPERTY, "unknown", 0, "", "");
		  error.addProperty(msg);
		  error.addProperty(code);
	  } finally {
		  onResponse(response);
	  }
  }
  
  @Override
  public void abort(String reason) {
	  logger.warn("Aborting navajomap: "+reason);
  }

  public void endTransaction() throws IOException {

  }

  public Navajo getInputNavajo() throws IOException {
	  return null;
  }


  public boolean isAborted() {
	  // TODO Auto-generated method stub
	  return false;
  }

  public boolean isCommitted() {
	  // TODO Auto-generated method stub
	  return false;
  }

  public void setCommitted(boolean b) {
	  // TODO Auto-generated method stub

  }

  public void setException(Exception e) {
	  myException = e;
  }

  public Exception getException() {
	  return myException;
  }
  
  public void setScheduledAt(long currentTimeMillis) {
	  // TODO Auto-generated method stub

  }

  // FIXME I think this is a bit strange
  public void setResponseNavajo(Navajo n)  {
		logger.warn("Set input navajo in NavajoMap... Isn't this odd? Shouldn't it be the output navajo?");	
		inDoc = n;
	}

public String getUrl() {
	return "TEMPORARY VERSION!!!";
	//return this.access.getRequestUrl();
}





public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException,
		NavajoException {
	// TODO Auto-generated method stub
	
}

@Override
public RequestQueue getRequestQueue() {
	return myRequestQueue;
}

@Override
public void setRequestQueue(RequestQueue myQueue) {
	myRequestQueue = myQueue;
}

@Override
public Object getAttribute(String name) {
	return attributes.get(name);
}

@Override
public void setAttribute(String name, Object value) {
	attributes.put(name, value);
}

@Override
public Set<String> getAttributeNames() {
	return Collections.unmodifiableSet(attributes.keySet());
}

@Override
public Navajo getResponseNavajo() {
	return inDoc;
}

@Override
public AsyncRequest getRequest() {
	logger.warn("No asyncrequest in NavajoMap. Returning null.");
	return null;
}

public String getResource() {
	return resource;
}

public void setResource(String resourceName) {
	this.resource = resourceName;
}

}
