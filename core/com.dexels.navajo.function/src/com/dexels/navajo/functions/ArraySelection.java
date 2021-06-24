/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ArraySelection extends FunctionInterface {

	

	@Override
	public String remarks() {
		// 'MessagePath' 'NameProperty' 'ValueProperty'
		return "Checks whether properties in an array message have unique values";
	}

	@Override
	public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

		if (getOperands().size() < 3) {
			throw new TMLExpressionException(this, usage());
		}

		String messageName = getStringOperand(0);
		String propertyName = getStringOperand(1);
		String valuePropertyName = getStringOperand(2);

//		Message parent = getCurrentMessage();
		Navajo doc = getNavajo();
		Message array = doc.getMessage(messageName);
		if (array == null) {
			throw new TMLExpressionException(this, "Empty or non existing array message: " + messageName);
		}

		List<Message> arrayMsg = array.getAllMessages();

		List<Selection> result = new ArrayList<Selection>();
		if (arrayMsg == null) {
			throw new TMLExpressionException(this, "Empty or non existing array message: " + messageName);
		}
		for (int i = 0; i < arrayMsg.size(); i++) {
			Message m = arrayMsg.get(i);
			Property p = m.getProperty(propertyName);
			Property val = m.getProperty(valuePropertyName);
			Selection s = NavajoFactory.getInstance().createSelection(doc, "" + p.getTypedValue(), "" + val.getTypedValue(), false);
			result.add(s);
		}
		return result;
	}

	@Override
	public String usage() {
		return "ArraySelection(<Array message name (absolute path)>,<Name Property name>,<Value Property name>)";
	}

}