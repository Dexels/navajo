package com.dexels.navajo.tipi.actions;

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
			ex.printStackTrace();
		}
		if (t != null) {
			t.performMethod(name, this, event);
		} else {
			myContext.debugTipiComponentTree(getComponent().getTipiParent(), 4);
			System.err.println("My parent: " + getComponent().getPath());
			System.err.println("My parentparent: "
					+ getComponent().getTipiParent().getPath());
			throw new TipiException(
					"performTipiMethod: Can not locate tipicomponent name: "
							+ path + " method: " + name);
		}
	}
}
