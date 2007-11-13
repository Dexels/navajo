package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.client.*;
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
public class TipiCallService extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		
		String unevaluated = getParameter("input").getValue();
		Operand serviceOperand = getEvaluatedParameter("service", event);
		Operand inputOperand = getEvaluatedParameter("input", event);
		if (serviceOperand == null || serviceOperand.value == null) {
			throw new TipiException("Error in callService action: service parameter missing!");
		}
		String service = (String) serviceOperand.value;
		Navajo input = null;
		if(inputOperand!=null) {
			input = (Navajo) inputOperand.value;
		}

		if(unevaluated!=null && input==null) {
			throw new TipiException("Input navajo not found when calling service: "+service+" supplied input: "+unevaluated);
		}
		if(input==null) {
//			throw new TipiException("Input navajo not found when calling service: "+service);
			input = NavajoFactory.getInstance().createNavajo();
		}

//		myContext.doSimpleSend(input, service, getComponent(), getExecutableChildCount(), false)
		try {
			
			Navajo nn = input.copy();
		//nn
			// Don't let NavajoClient touch your original navajo! It will mess things up.
			myContext.fireNavajoSent(input, service);
			
			Navajo result = NavajoClientFactory.getClient().doSimpleSend(nn, service);
			myContext.fireNavajoReceived(result, service);

			myContext.addNavajo(service, result);
			// is this correct? It is a bit odd.
			if(result.getHeader()!=null) {
				result.getHeader().setHeaderAttribute("sourceScript", result.getHeader().getRPCName());
			}
			myContext.loadNavajo(result, service);

			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
