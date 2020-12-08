/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.components.swingimpl.TipiMessageDialog;
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
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiAskValue extends TipiAction {

	private static final long serialVersionUID = 8276253817431008950L;

	public TipiAskValue() {
	}

	@Override
	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		final Operand text = getEvaluatedParameter("text", event);
		final Operand globalvalue = getEvaluatedParameter("value", event);
		final Operand initialValue = getEvaluatedParameter("initialValue",
				event);
		String initVal = "";
		if (initialValue != null) {
			if (initialValue.value != null) {
				initVal = "" + initialValue.value;
			}
		}
		TipiMessageDialog info = new TipiMessageDialog(event.getComponent());
		info.initialize(myContext);
		info.initializeAskValue(initVal, "" + globalvalue.value);
		info.setValue("text", text.value);
		info.setValue("title", "");
		info.setValue("messageType", -2);
		info.setValue("cssClass", text.value);
		info.componentInstantiated();
		info.initContainer();
		
	}
}
