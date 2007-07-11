package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
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
public class TipiPerformTipiMethod extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		TipiComponent t = null;
		String name = null;
		String path = null;
		try {
			path = getParameter("path").getValue();
			t = (TipiComponent) myContext.evaluate(path, myComponent, event).value;
			name = (String) evaluate(getParameter("name").getValue(), event).value;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (t != null) {
			t.performMethod(name, this, event);
		} else {
			throw new TipiException("performTipiMethod: Can not locate tipicomponent name: " + path + " method: " + name);
		}
	}
}
