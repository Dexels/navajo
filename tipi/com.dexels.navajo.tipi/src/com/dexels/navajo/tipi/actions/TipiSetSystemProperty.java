/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
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

public class TipiSetSystemProperty extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2528847214946786108L;

	public TipiSetSystemProperty() {
	}

	@Override
	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand name = getEvaluatedParameter("name", event);
		Operand value = getEvaluatedParameter("value", event);
		if (name == null) {
			return;
		}
		if (value == null) {
			getContext().setSystemProperty(name.value.toString(), null);
			return;
		}
		getContext().setSystemProperty(name.value.toString(), value.value.toString());
//		System.setProperty(name.value.toString(), value.value.toString());
	}

}
