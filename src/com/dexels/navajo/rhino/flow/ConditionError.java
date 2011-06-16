package com.dexels.navajo.rhino.flow;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class ConditionError extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5199659554341354411L;
	private Navajo conditionNavajo;
	private Message conditionMessage;

	public ConditionError() {
		conditionNavajo = NavajoFactory.getInstance().createNavajo();
		conditionMessage = NavajoFactory.getInstance().createMessage(
				conditionNavajo, "ConditionErrors", Message.MSG_TYPE_ARRAY);
		try {
			conditionNavajo.addMessage(conditionMessage);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	public Navajo getConditionErrors() {
		return conditionNavajo;
	}

	public void append(int code, String description) {
		Message m = NavajoFactory.getInstance().createMessage(conditionNavajo,
				"ConditionErrors", Message.MSG_TYPE_ARRAY_ELEMENT);
		conditionMessage.addElement(m);
		try {
			Property codeProperty = NavajoFactory.getInstance().createProperty(
					conditionNavajo, "Code", Property.INTEGER_PROPERTY,
					"" + code, 0, "", Property.DIR_OUT);
			Property descriptionProperty = NavajoFactory.getInstance()
					.createProperty(conditionNavajo, "Description",
							Property.STRING_PROPERTY, description, 0, "",
							Property.DIR_OUT);
			m.addProperty(codeProperty);
			m.addProperty(descriptionProperty);
			System.err.println("Current condition errors: ");
			conditionNavajo.write(System.err);

		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean hasConditionErrors() {
		return conditionMessage.getArraySize() > 0;
	}

}
