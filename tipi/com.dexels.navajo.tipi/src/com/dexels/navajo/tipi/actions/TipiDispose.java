package com.dexels.navajo.tipi.actions;

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
public class TipiDispose extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2702919442460647651L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		try {
			TipiComponent tp = (TipiComponent) getEvaluatedParameterValue(
					"path", event);
			// String pathVal = getParameter("path").getValue();
			// TipiComponent tp = (TipiComponent) evaluate(pathVal,
			// event).value;
			if (tp != null) {
				// System.err.println("ATTEMPTING TO DISPOSE: " + tp.getPath());
				myContext.disposeTipiComponent(tp);
			} else {
				System.err.println("ATTEMPTING TO DISPOSE NULL component. ");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
