package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
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
public class TipiInjectMessage extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8940738100980864157L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		Operand messageOperand = getEvaluatedParameter("message", event);
		Operand serviceOperand = getEvaluatedParameter("service", event);

		if (messageOperand == null || messageOperand.value == null) {
			throw new TipiException(
					"Error injecting message: No message supplied!");
		}
		if (serviceOperand == null || serviceOperand.value == null) {
			throw new TipiException(
					"Error injecting message: No service supplied!");
		}
		Message message = (Message) messageOperand.value;
		String service = (String) serviceOperand.value;

		Navajo nn = NavajoFactory.getInstance().createNavajo();
		nn.addHeader(NavajoFactory.getInstance().createHeader(nn, service, "",
				"", -1));
		try {
			nn.addMessage(message);
		} catch (NavajoException e) {
			throw new TipiException(
					"Error injecting message: No service supplied!", e);
		}

		myContext.injectNavajo(service, nn);

	}
}
