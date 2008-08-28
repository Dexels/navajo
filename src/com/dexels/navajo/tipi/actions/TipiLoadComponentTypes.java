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
public class TipiLoadComponentTypes extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand contextParam = getEvaluatedParameter("context", event);
		TipiContext context = null;
		if(contextParam!=null) {
			context = (TipiContext) contextParam.value;
			if(context==null) {
				context = myContext;
			}
			
		} else {
			context = myContext;
		}
		String service = null;
		Operand serviceOperand = getEvaluatedParameter("service", event);
		if(serviceOperand!=null) {
			if(serviceOperand.value!=null) {
				service = (String) serviceOperand.value;
			}
		}
		if(service==null) {
			service = "ComponentNavajo";
		}
		

		try {
			Navajo compNavajo = context.createComponentNavajo();
			context.loadNavajo(compNavajo, service);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}
}