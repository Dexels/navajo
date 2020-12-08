/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

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
public class TipiLoadStateNavajo extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1681280526959616752L;

	@Override
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
			service = "StateNavajo";
		}

		context.loadNavajo(context.getStateNavajo(), service);
	}
}