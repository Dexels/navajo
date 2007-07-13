package com.dexels.navajo.tipi.actions;

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
public class TipiAddProperty extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand pathOperand = getEvaluatedParameter("path", event);
		Operand valueOperand = getEvaluatedParameter("value", event);
		Operand navajoOperand = getEvaluatedParameter("navajo", event);
		Operand directionOperand = getEvaluatedParameter("direction", event);
		Operand descriptionOperand = getEvaluatedParameter("description", event);

		if (pathOperand == null || pathOperand.value == null) {
			throw new TipiException("Error in addProperty action: path parameter missing!");
		}
		if (valueOperand == null || valueOperand.value == null) {
			throw new TipiException("Error in addProperty action: value parameter missing!");
		}
		if (navajoOperand == null || navajoOperand.value == null) {
			throw new TipiException("Error in addProperty action: navajo parameter missing!");
		}
		if (directionOperand == null || directionOperand.value == null) {
			throw new TipiException("Error in addProperty action: direction parameter missing!");
		}
		String path = (String) pathOperand.value;
		if (path.indexOf("/") == -1) {
			// no slashes:
			throw new TipiException("Error in addProperty action: Invalid path: " + path);
		}
		String propertyName = path.substring(path.lastIndexOf("/") + 1, path.length());
		String messagePath = path.substring(0, path.lastIndexOf("/"));

		Navajo n = (Navajo) navajoOperand.value;

		Message parentMessage = null;
		Property p = n.getProperty(path);
		if (p != null) {
			System.err.println("Property exists!");
			parentMessage = p.getParentMessage();
			parentMessage.removeProperty(p);
		} else {
			parentMessage = n.getMessage(messagePath);
		}

		if (parentMessage == null) {
			parentMessage = NavajoFactory.getInstance().createMessage(n, messagePath);
			try {
				n.addMessage(parentMessage);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			throw new TipiException("Error in addProperty action: Path not found: " + path);
		}

		String direction = (String) directionOperand.value;
		if (direction == null) {
			direction = Property.DIR_IN;
		}
		if (!Property.DIR_IN.equals(direction) && (!Property.DIR_OUT.equals(direction))) {
			direction = Property.DIR_IN;
		}
		String description = null;
		if(descriptionOperand!=null) {
			description = (String) descriptionOperand.value;
		}
		try {
			Property q = NavajoFactory.getInstance().createProperty(n, propertyName, Property.STRING_PROPERTY, null, 0,
					description, direction);
			q.setAnyValue(valueOperand.value);
			parentMessage.addProperty(q);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException("Error creating property: " + path);
		}
	}

	public static void main(String[] args) {
		String path = "1234/5678/90ab";
		System.err.println(path);
		String name = path.substring(path.lastIndexOf("/") + 1, path.length());
		String pp = path.substring(0, path.lastIndexOf("/"));
		System.err.println(name);
		System.err.println(pp);

	}
}
// <tipiaction name="addProperty" class="TipiAddProperty"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="navajo" type="navajo required="true""/>
// </tipiaction>
//
// <tipiaction name="addPropertyToMessage" class="TipiAddPropertyToMessage"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="message" type="message" required="true"/>
// </tipiaction>
//
// <tipiaction name="insertMessage" class="TipiInsertMessage"
// package="com.dexels.navajo.tipi.actions">
// <param name="path" type="string" required="true"/>
// <param name="value" type="object" required="true"/>
// <param name="message" type="message" required="true"/>
// </tipiaction>
