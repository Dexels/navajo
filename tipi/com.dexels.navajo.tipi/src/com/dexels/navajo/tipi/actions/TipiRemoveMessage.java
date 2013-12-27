package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
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
public class TipiRemoveMessage extends TipiAction {
	private static final long serialVersionUID = 7956701750993369325L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiRemoveMessage.class);
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand messageOperand = getEvaluatedParameter("message", event);
		if (messageOperand == null) {
			throw new TipiException(
					"Error in insertMessage action: navajo message missing!");
		}
		if (messageOperand.value == null) {
			return;
		}

		Message message = (Message) messageOperand.value;
		// if (!Message.MSG_TYPE_ARRAY.equals(message.getType())) {
		// throw new
		// TipiException("Error in insertMessage. InsertMessage only works for array messages!");
		// }
		Operand index = getEvaluatedParameter("index", event);
		if (index != null && index.value != null) {
			Integer ii = (Integer) index.value;
			// TODO: Perhaps refactor into NavajoDocument
			Message mm = message.getMessage(ii.intValue());
			message.removeMessage(mm);
			try {
				myContext.unlink(mm.getRootDoc(), mm);
			} catch (NavajoException e) {
				throw new TipiException("Error unlinking message: "
						+ mm.getFullMessageName(), e);
			}
		} else {
			// we are in non array mode now.
			Message parent = message.getParentMessage();
			if (parent == null) {
				// toplevel? Remove from navajo
				Navajo root = message.getRootDoc();
				if (root == null) {
					// yes, well... ByE!
					return;
				} else {
					try {
						root.removeMessage(message);
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}
				}
			} else {
				parent.removeMessage(message);
			}
		}

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
