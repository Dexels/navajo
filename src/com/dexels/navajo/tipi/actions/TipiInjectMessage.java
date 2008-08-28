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
public class TipiInjectMessage extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		
		Operand messageOperand = getEvaluatedParameter("message", event);
		Operand serviceOperand = getEvaluatedParameter("service", event);

		if(messageOperand==null || messageOperand.value==null) {
			throw new TipiException("Error injecting message: No message supplied!");
		}
		if(serviceOperand==null || serviceOperand.value==null) {
			throw new TipiException("Error injecting message: No service supplied!");
		}
		Message message = (Message) messageOperand.value;
		String service = (String) serviceOperand.value;
		
		Navajo nn = NavajoFactory.getInstance().createNavajo();
		try {
			nn.addMessage(message);
		} catch (NavajoException e) {
				throw new TipiException("Error injecting message: No service supplied!",e);
		}
		
		myContext.injectNavajo(service, nn);


	}
}
