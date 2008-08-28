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
public class TipiLoadNavajoList extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand contextParam = getEvaluatedParameter("context", event);
		TipiContext context = null;
		if(contextParam!=null) {
			context = (TipiContext) contextParam.value;
			if(context==null) {
				context = myContext;
				System.err.println("CONTEXT RESOLVED!");
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
			service = "NavajoListNavajo";
		}
		
		
		
		try {
			Navajo compNavajo = context.createNavajoListNavajo();
			context.loadNavajo(compNavajo, service);
			compNavajo.write(System.err);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}
}