package com.dexels.navajo.adapter;

import java.util.Iterator;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class CopyMessage implements Mappable {

  private Navajo outputDoc;
  private Navajo inDoc;
  private Access myAccess;
  public boolean useOutputDoc = true;
  public boolean useDefinitionMessage = false;
  
  public String copyMessageFrom = null; // If copyMessageFrom is empty, either the currently processed incoming (useOutputDoc = false)
  									  // or currently processed outgoing (useOutputDoc = true) will be copied.
  public String copyMessageTo = null;
 
  public void load(Access access) throws MappableException, UserException {
    outputDoc = access.getOutputDoc();
    inDoc = access.getInDoc();
    myAccess = access;
  }

  private void copy(Message from, Message to) {
	  
	  // Copy scope.
	  if ( from.getScope() != null && !from.getScope().equals("") ) {
		  to.setScope(from.getScope());
	  }
	  
	  // Copy extends.
	  if ( from.getExtends() != null && !from.getExtends().equals("") ) {
		  to.setExtends(from.getExtends());
	  }
	  
	  if ( from.isArrayMessage() && from.getDefinitionMessage() != null ) {
		  to.setDefinitionMessage(from.getDefinitionMessage().copy(outputDoc));
	  }
	  // Copy properties.
	  Iterator<Property> allProperties = from.getAllProperties().iterator();
	  while ( allProperties.hasNext() ) {
		  Property p = allProperties.next();
		  to.addProperty(p.copy(outputDoc));
	  }
	  // Copy messages.
	  Iterator<Message> allMessages = from.getAllMessages().iterator();
	  while ( allMessages.hasNext() ) {
		  Message m = allMessages.next();
		  to.addMessage(m.copy(outputDoc));
	  }

  }
  
  public void store() throws MappableException, UserException {

//    if (copyMessageTo.equals(""))
//      throw new UserException( -1, "copyMessageTo has to be specified");

	
    Message from = null;
    if ( copyMessageFrom == null ) {
    	from = (useOutputDoc) ? myAccess.getCompiledScript().getCurrentOutMsg() : myAccess.getCompiledScript().getCurrentInMsg();
    } else {
    	from = (useOutputDoc) ? outputDoc.getMessage(this.copyMessageFrom) : inDoc.getMessage(this.copyMessageFrom);
    	if ( useDefinitionMessage ) {
    		if ( !from.isArrayMessage() || from.getDefinitionMessage() == null ) {
    			throw new UserException(-1, "Could not copy definition message: not present.");
    		}
    		from = from.getDefinitionMessage();
    	}
    }
    
    if (from == null)
      throw new UserException( -1,
                              "Could not find message " + this.copyMessageFrom +
                              " in output document");
    
    Message to = null;
    
    if ( copyMessageTo != null ) {
    	try {
    		to = MappingUtils.addMessage(outputDoc, myAccess.getCurrentOutMessage(), copyMessageTo, null, 
    				1, ( from.getType() != Message.MSG_TYPE_DEFINITION ? from.getType() : Message.MSG_TYPE_SIMPLE ), "")[0];
    	} catch (Exception e1) {
    		throw new UserException(-1, e1.getMessage(), e1);
    	}
    } else {
    	if ( myAccess.getCompiledScript().getCurrentOutMsg() == null ) {
    		throw new UserException(-1, "No current message available for copy message.");
    	}
    	to = myAccess.getCompiledScript().getCurrentOutMsg();
    }

    copy(from, to);
   
  }

  public void kill() {

  }

  public void setUseOutputDoc(boolean b) {
    this.useOutputDoc = b;
  }
  
  public void setUseDefinitionMessage(boolean b) {
	    this.useDefinitionMessage = b;
	  }

  public void setCopyMessageFrom(String name) {
    this.copyMessageFrom = name;
  }

  public void setCopyMessageTo(String name) {
    this.copyMessageTo = name;
  }

}
