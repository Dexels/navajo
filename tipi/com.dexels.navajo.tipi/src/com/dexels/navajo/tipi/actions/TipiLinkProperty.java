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
public final class TipiLinkProperty extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1289744637248158763L;

	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		final Property master = (Property) getEvaluatedParameter("master",
				event).value;
		final Property slave = (Property) getEvaluatedParameter("slave", event).value;

		myContext.link(master, slave);

	}

}
