package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
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
public class TipiAddPropertyToMessage extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5296764161841844878L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand pathOperand = getEvaluatedParameter("path", event);
		Operand valueOperand = getEvaluatedParameter("value", event);
		Operand messageOperand = getEvaluatedParameter("message", event);
		Operand directionOperand = getEvaluatedParameter("direction", event);
		Operand descriptionOperand = getEvaluatedParameter("description", event);

		if (pathOperand == null || pathOperand.value == null) {
			throw new TipiException(
					"Error in addProperty action: path parameter missing!");
		}
		if (valueOperand == null || valueOperand.value == null) {
			throw new TipiException(
					"Error in addProperty action: value parameter missing!");
		}
		if (messageOperand == null || messageOperand.value == null) {
			throw new TipiException(
					"Error in addProperty action: navajo parameter missing!");
		}
		if (directionOperand == null || directionOperand.value == null) {
			throw new TipiException(
					"Error in addProperty action: direction parameter missing!");
		}
		String path = (String) pathOperand.value;
		if (path.indexOf("/") == -1) {
			// no slashes:
			throw new TipiException(
					"Error in addProperty action: Invalid path: " + path);
		}
		String propertyName = path.substring(path.lastIndexOf("/") + 1,
				path.length());
		String messagePath = path.substring(0, path.lastIndexOf("/"));

		Message m = (Message) messageOperand.value;

		Message parentMessage = null;
		Property p = m.getProperty(path);
		if (p != null) {
			parentMessage = p.getParentMessage();
			parentMessage.removeProperty(p);
		} else {
			parentMessage = m.getMessage(messagePath);
		}

		if (parentMessage == null) {
			throw new TipiException(
					"Error in addProperty action: Path not found: " + path);
		}

		String direction = (String) directionOperand.value;
		if (direction == null) {
			direction = Property.DIR_IN;
		}
		if (!Property.DIR_IN.equals(direction)
				&& (!Property.DIR_OUT.equals(direction))) {
			direction = Property.DIR_IN;
		}
		String description = null;
		if(descriptionOperand==null || descriptionOperand.value==null) {
			description = "";
		} else {
			description = (String) descriptionOperand.value;
			
		}
		try {
			Property q = NavajoFactory.getInstance().createProperty(
					m.getRootDoc(), propertyName, Property.STRING_PROPERTY,
					null, 0, description, direction);
			q.setAnyValue(valueOperand.value);
			parentMessage.addProperty(q);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException("Error creating property: " + path);
		}
	}

	public static void main(String[] args) {
		String path = "1234/5678/90ab";
		System.out.println(path);
		String name = path.substring(path.lastIndexOf("/") + 1, path.length());
		String pp = path.substring(0, path.lastIndexOf("/"));
		System.out.println(name);
		System.out.println(pp);

	}
}
