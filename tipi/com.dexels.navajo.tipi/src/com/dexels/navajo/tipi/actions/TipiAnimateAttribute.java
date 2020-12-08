/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
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
public final class TipiAnimateAttribute extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736435718317964879L;

	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		String attribute = (String) getEvaluatedParameterValue("attribute",
				event);
		int duration = (Integer) getEvaluatedParameterValue("duration", event);
		TipiComponent tc = (TipiComponent) getEvaluatedParameterValue("target",
				event);

		final Property target = tc.getAttributeProperty(attribute);
		final Object value = getEvaluatedParameterValue("value", event);

		myContext.animateProperty(target, duration, value);
	}

}
