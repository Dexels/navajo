package com.dexels.navajo.tipi.actions;

import java.util.ArrayList;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

// <tipiaction name="insertMessage" class="TipiInsertMessage"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="message" type="message" required="true"/>
// </tipiaction>
public class TipiRemoveAllMessages extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand messageOperand = getEvaluatedParameter("message", event);
		if (messageOperand == null ) {
			throw new TipiException("Error in delete action: navajo message missing!");
		}
		if(messageOperand.value == null) {
			return;
		}
		
		Message message = (Message) messageOperand.value;
		if (!Message.MSG_TYPE_ARRAY.equals(message.getType())) {
			throw new TipiException("Error in deleteMessage. removeAllMessages only works for array messages!");
		}
		
		if(message.getAllMessages()==null) {
			return;
		}
		while(message.getArraySize()>0) {
			message.removeMessage(message.getMessage(0));
		}
	}

}
