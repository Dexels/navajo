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

	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand name = getEvaluatedParameter("name", event);
		Operand value = getEvaluatedParameter("value", event);
		if (name == null || value == null) {
			return;
		}
		System.setProperty(name.value.toString(), value.value.toString());
	}

}
