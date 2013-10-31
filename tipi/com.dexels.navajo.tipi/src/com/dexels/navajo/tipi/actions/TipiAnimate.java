package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Property;
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
public final class TipiAnimate extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7982136701702106731L;

	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		Object valueObject = getEvaluatedParameter("target", event).value;
		final Property target = (Property) valueObject;

		final Object value = getEvaluatedParameter("value", event).value;

		myContext.animateProperty(target, 500, value);
	}

}
