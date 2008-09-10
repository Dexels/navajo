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

public class TipiReloadNavajo extends TipiAction {
	public TipiReloadNavajo() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
		Operand to = getEvaluatedParameter("to", event);
		Operand from = getEvaluatedParameter("from", event);
		Operand service = getEvaluatedParameter("service", event);
		String serviceName = null;
		Navajo actualNavajo = null;
		if (to == null || from == null) {

			// System.err.println(
			// "Null evaluation in TipiReloadNavajo, new school evaluation");
			if (service.value instanceof String) {
				serviceName = (String) service.value;

				service.value = myContext.getNavajo(serviceName);
				actualNavajo = (Navajo) service.value;
			} else {
				if (service.value instanceof Navajo) {
					actualNavajo = (Navajo) service.value;
					serviceName = actualNavajo.getHeader().getRPCName();
				}
			}
			myContext.loadNavajo(actualNavajo, serviceName);
			return;
		}
		// System.err.println("Old school navajoReload found!");
		TipiDataComponent toData = (TipiDataComponent) to.value;
		TipiDataComponent fromData = (TipiDataComponent) from.value;

		toData.loadData(fromData.getNearestNavajo(), toData.getCurrentMethod());

	}

}
