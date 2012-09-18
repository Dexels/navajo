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
  public String copyMessageFrom = ""; // If copyMessageFrom is empty, either the currently processed incoming (useOutputDoc = false)
  									  // or currently processed outgoing (useOutputDoc = true) will be copied.
  public String copyMessageTo = "";

  public void load(Access access) throws MappableException, UserException {
    outputDoc = access.getOutputDoc();
    inDoc = access.getInDoc();
    myAccess = access;
  }

  public void store() throws MappableException, UserException {

    if (copyMessageTo.equals(""))
      throw new UserException( -1, "copyMessageTo has to be specified");

    Message from = null;
    if ( copyMessageFrom.equals("") ) {
    	from = (useOutputDoc) ? myAccess.getCompiledScript().getCurrentOutMsg() : myAccess.getCompiledScript().getCurrentInMsg();
    } else {
    	from = (useOutputDoc) ? outputDoc.getMessage(this.copyMessageFrom) : inDoc.getMessage(this.copyMessageFrom);
    }
    
    if (from == null)
      throw new UserException( -1,
                              "Could not find message " + this.copyMessageFrom +
                              " in output document");
    Message to = null;
    
    try {
		to = MappingUtils.addMessage(outputDoc, myAccess.getCurrentOutMessage(), copyMessageTo, null, 
				1, from.getType(), "")[0];
	} catch (Exception e1) {
		throw new UserException(-1, e1.getMessage(), e1);
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

  public void kill() {

  }

  public void setUseOutputDoc(boolean b) {
    this.useOutputDoc = b;
  }

  public void setCopyMessageFrom(String name) {
    this.copyMessageFrom = name;
  }

  public void setCopyMessageTo(String name) {
    this.copyMessageTo = name;
  }

}
