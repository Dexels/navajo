package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiDataComponent;
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
 * @deprecated
 */

@Deprecated
public class TipiReloadNavajo extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7809531027401231070L;

	public TipiReloadNavajo() {
	}

	@Override
	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand to = getEvaluatedParameter("to", event);
		Operand from = getEvaluatedParameter("from", event);
		Operand service = getEvaluatedParameter("service", event);
		String serviceName = null;
		Navajo actualNavajo = null;
		if (to == null || from == null) {

			if (service.value instanceof String) {
				serviceName = (String) service.value;

//				service.value = myContext.getNavajo(serviceName);
				actualNavajo = (Navajo) myContext.getNavajo(serviceName);
				myContext.loadNavajo(actualNavajo, serviceName);
			} else {
				if (service.value instanceof Navajo) {
					actualNavajo = (Navajo) service.value;
					serviceName = actualNavajo.getHeader().getRPCName();
					myContext.loadNavajo(actualNavajo, serviceName);
				}
			}
			return;
		}
		TipiDataComponent toData = (TipiDataComponent) to.value;
		TipiDataComponent fromData = (TipiDataComponent) from.value;
		toData.loadData(fromData.getNearestNavajo(), toData.getCurrentMethod());

	}

}
