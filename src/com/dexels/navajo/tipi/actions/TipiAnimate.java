package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
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
public final class TipiAnimate extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		final Property target = (Property) getEvaluatedParameter("target", event).value;
		final Object value = getEvaluatedParameter("value", event).value;

		myContext.animateProperty(target, 500, value);
	}

}
