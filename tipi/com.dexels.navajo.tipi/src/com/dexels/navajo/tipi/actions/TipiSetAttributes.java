/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import java.util.Set;

import com.dexels.navajo.tipi.TipiComponent;
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
public final class TipiSetAttributes extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 472131866710083014L;

	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// Set<String> ss = getParameterNames();

		Object o = getEvaluatedParameterValue("path", event);
		if (o == null) {
			Object path = getParameter("path");
			throw new TipiException(
					"TipiSetAttributes: Path component missing (from path: " + path + ")");

		}
		if (!(o instanceof TipiComponent)) {
			Object path = getParameter("path");
			throw new TipiException(
					"TipiSetAttributes: Path component wrong type (from path: " + path + ")");
		}
		TipiComponent tc = (TipiComponent) o;
		Set<String> p = getParameterNames();
		for (String name : p) {
			if (name.equals("path")) {
				// Path is reserved
				continue;
			}
			Object oo = getEvaluatedParameterValue(name, event);
			tc.setValue(name, oo);
		}
	}
}
