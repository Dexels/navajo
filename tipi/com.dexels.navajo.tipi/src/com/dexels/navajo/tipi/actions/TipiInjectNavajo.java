package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Navajo;
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
public class TipiInjectNavajo extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9055012774609402605L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		Operand navajoOperand = getEvaluatedParameter("navajo", event);
		Operand serviceOperand = getEvaluatedParameter("service", event);

		if (navajoOperand == null || navajoOperand.value == null) {
			throw new TipiException(
					"Error injecting navajo: No navajo supplied!");
		}
		if (serviceOperand == null || serviceOperand.value == null) {
			throw new TipiException(
					"Error injecting message: No service supplied!");
		}
		Navajo navajo = (Navajo) navajoOperand.value;
		String service = (String) serviceOperand.value;
		myContext.injectNavajo(service, navajo);

	}
}
