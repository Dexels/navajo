package com.dexels.navajo.rhino.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Navajo conditionNavajo = null;
	private Message conditionMessage;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ConditionError.class);
	
	public ConditionError() {
	}

	public Navajo getConditionErrors() {
		return conditionNavajo;
	}

	public void append(String code, String description) {
		
		if ( conditionNavajo == null ) {
			conditionNavajo = NavajoFactory.getInstance().createNavajo();
			conditionMessage = NavajoFactory.getInstance().createMessage(
					conditionNavajo, "ConditionErrors", Message.MSG_TYPE_ARRAY);
			try {
				conditionNavajo.addMessage(conditionMessage);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		}
		
		Message m = NavajoFactory.getInstance().createMessage(conditionNavajo,
				"ConditionErrors", Message.MSG_TYPE_ARRAY_ELEMENT);
		conditionMessage.addElement(m);
		try {
			Property codeProperty = NavajoFactory.getInstance().createProperty(
					conditionNavajo, "Id", Property.STRING_PROPERTY,
					"" + code, 0, "", Property.DIR_OUT);
			Property descriptionProperty = NavajoFactory.getInstance()
					.createProperty(conditionNavajo, "Description",
							Property.STRING_PROPERTY, description, 0, "",
							Property.DIR_OUT);
			m.addProperty(codeProperty);
			m.addProperty(descriptionProperty);
			logger.info("Current condition errors: ");
			conditionNavajo.write(System.err);

		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
	}

	public boolean hasConditionErrors() {
		return (conditionMessage != null && conditionMessage.getArraySize() > 0);
	}

}
