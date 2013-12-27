package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
	public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

		if (getOperands().size() < 3) {
			throw new TMLExpressionException(this, usage());
		}

		for (int i = 0; i < getOperands().size(); i++) {
			Object o = getOperands().get(i);
			System.err.println("Operand # " + i + " is: " + o.toString() + " - " + o.getClass());
		}

		if (!(getOperand(0) instanceof String && getOperand(1) instanceof String && getOperand(2) instanceof String)) {
			throw new TMLExpressionException(this, usage());
		}
		String messageName = (String) getOperand(0);
		String propertyName = (String) getOperand(1);
		String valuePropertyName = (String) getOperand(2);

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