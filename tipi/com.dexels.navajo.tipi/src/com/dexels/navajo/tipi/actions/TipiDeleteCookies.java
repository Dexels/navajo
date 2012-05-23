package com.dexels.navajo.tipi.actions;

import java.io.IOException;

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
public class TipiDeleteCookies extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3025724832308222502L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		try {
			myContext.getCookieManager().deleteCookies();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}