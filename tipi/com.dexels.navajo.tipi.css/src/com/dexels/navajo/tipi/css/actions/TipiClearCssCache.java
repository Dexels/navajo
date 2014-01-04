package com.dexels.navajo.tipi.css.actions;

import com.dexels.navajo.tipi.css.CssFactory;
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
public class TipiClearCssCache extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5928058510320346138L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		CssFactory.getInstance().clearCssDefinitions();
		// System.exit(0);
	}
}