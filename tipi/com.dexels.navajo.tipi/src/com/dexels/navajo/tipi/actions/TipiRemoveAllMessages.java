/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
public class TipiRemoveAllMessages extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6814896223185805866L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand messageOperand = getEvaluatedParameter("message", event);
		if (messageOperand == null) {
			throw new TipiException(
					"Error in delete action: navajo message missing!");
		}
		if (messageOperand.value == null) {
			return;
		}

		Message message = (Message) messageOperand.value;
		if (!Message.MSG_TYPE_ARRAY.equals(message.getType())) {
			throw new TipiException(
					"Error in deleteMessage. removeAllMessages only works for array messages!");
		}

		if (message.getAllMessages() == null) {
			return;
		}
		while (message.getArraySize() > 0) {
			message.removeMessage(message.getMessage(0));
		}
	}

}
