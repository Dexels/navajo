package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiInsertArrayElement extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -500931972454976551L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand messageOperand = getEvaluatedParameter("message", event);
		if (messageOperand == null || messageOperand.value == null) {
			throw new TipiException(
					"Error in insertMessage action: navajo message missing!");
		}
		Message arrayMessage = (Message) messageOperand.value;
		if (!Message.MSG_TYPE_ARRAY.equals(arrayMessage.getType())) {
			throw new TipiException(
					"Error in insertMessage. InsertMessage only works for array messages!");
		}

		Operand elementOperand = getEvaluatedParameter("element", event);
		if (elementOperand == null || elementOperand.value == null) {
			throw new TipiException(
					"Error in insertMessage action: navajo element missing!");
		}
		Message elementMessage = (Message) elementOperand.value;
		if (Message.MSG_TYPE_ARRAY.equals(elementMessage.getType())) {
			throw new TipiException(
					"Error in insertMessage. InsertMessage only works for inserting a NON array element into an array!");
		}

		arrayMessage.addMessage(elementMessage);
	}

}
