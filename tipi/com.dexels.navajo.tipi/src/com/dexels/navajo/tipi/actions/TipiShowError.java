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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Refactor, move to NavajoSwingTipi */
public class TipiShowError extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -138337054456517828L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// String txt = getParameter("text").getValue();
		final Operand op = getEvaluatedParameter("text", event);
		Object oo = op.value;
		String result = "";
		if (oo == null) {
			result = "Null value!";
		} else {
			result = oo.toString();
		}

        final String txt = (result).replaceAll("\n", " ");

        String title = (String) getEvaluatedParameterValue("title", event);
        if (title == null) {
            title = "Error";
        }

        myContext.showError(txt, title, event.getComponent());
	}
}
