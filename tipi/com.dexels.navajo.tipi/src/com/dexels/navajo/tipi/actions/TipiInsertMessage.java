package com.dexels.navajo.tipi.actions;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
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
public class TipiInsertMessage extends TipiAction {
	private static final long serialVersionUID = -2822233445200193988L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiInsertMessage.class);
	
	@Override
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
		Message definitionMessage = arrayMessage.getDefinitionMessage();
		if (definitionMessage == null) {
			if (arrayMessage.getArraySize() == 0) {
				throw new TipiException(
						"Error in insertMessage. InsertMessage only works for array messages with definition messages (or array content)!");
			}
			definitionMessage = arrayMessage.getMessage(arrayMessage
					.getArraySize() - 1);
		}

		// TODO: Perhaps refactor into NavajoDocument
		Message copy = definitionMessage.copy(arrayMessage.getRootDoc());
		copy.setType("");
		// TODO: Seems redundant. Message#copy does this
		List<Property> al = definitionMessage.getAllProperties();
		for (Iterator<Property> iter = al.iterator(); iter.hasNext();) {
			Property element = iter.next();
			copy.getProperty(element.getName()).setValue(element.getValue());
		}
		arrayMessage.addMessage(copy);

	}

	public static void main(String[] args) {
		String path = "1234/5678/90ab";
		logger.info(path);
		String name = path.substring(path.lastIndexOf("/") + 1, path.length());
		String pp = path.substring(0, path.lastIndexOf("/"));
		logger.info(name);
		logger.info(pp);

	}
}
