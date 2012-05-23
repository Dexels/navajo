package com.dexels.navajo.tipi.components.swingimpl.actions;

import com.dexels.navajo.tipi.components.swingimpl.jnlp.WebStartProxy;
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

public class TipiInjectJnlpCache extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8336648238926896081L;

	public TipiInjectJnlpCache() {
	}

	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		WebStartProxy.injectJnlpCache();
		// ((SwingTipiContext)myContext).

	}

}
