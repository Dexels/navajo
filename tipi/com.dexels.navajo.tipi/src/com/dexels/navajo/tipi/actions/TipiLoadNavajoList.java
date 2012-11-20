package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiContext;
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
public class TipiLoadNavajoList extends TipiAction {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiLoadNavajoList.class);
	
	private static final long serialVersionUID = 3429862023195715057L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand contextParam = getEvaluatedParameter("context", event);
		TipiContext context = null;
		if (contextParam != null) {
			context = (TipiContext) contextParam.value;
			if (context == null) {
				context = myContext;
			}

		} else {
			context = myContext;
		}
		String service = null;
		Operand serviceOperand = getEvaluatedParameter("service", event);
		if (serviceOperand != null) {
			if (serviceOperand.value != null) {
				service = (String) serviceOperand.value;
			}
		}
		if (service == null) {
			service = "NavajoListNavajo";
		}

		try {
			Navajo compNavajo = context.createNavajoListNavajo();
			context.loadNavajo(compNavajo, service);
			compNavajo.write(System.err);
		} catch (NavajoException e) {
			logger.error("Error: ",e);
		}
	}
}