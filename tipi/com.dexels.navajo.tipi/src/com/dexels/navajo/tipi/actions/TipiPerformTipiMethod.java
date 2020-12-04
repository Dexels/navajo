/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class TipiPerformTipiMethod extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2948114436573845551L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiPerformTipiMethod.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		TipiComponent t = null;
		String name = null;
		String path = null;
		try {
			path = getParameter("path").getValue();
			t = (TipiComponent) myContext.evaluate(path, getComponent(), event).value;
			name = (String) evaluate(getParameter("name").getValue(), event).value;
		} catch (Exception ex) {
			logger.error("Error: ",ex);
		}
		if (t != null) {
			t.performMethod(name, this, event);
		} else {
			myContext.debugTipiComponentTree(getComponent().getTipiParent(), 4);
			logger.error("My parent: " + getComponent().getPath());
			logger.error("My parentparent: "
					+ getComponent().getTipiParent().getPath());
			throw new TipiException(
					"performTipiMethod: Can not locate tipicomponent name: "
							+ path + " method: " + name);
		}
	}
}
