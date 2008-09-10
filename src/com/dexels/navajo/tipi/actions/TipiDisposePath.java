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
 * @deprecated
 * @author not attributable
 * @version 1.0
 */
@Deprecated
public class TipiDisposePath extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		try {
			String pathVal = (String) getEvaluatedParameter("path", event).value;
			TipiComponent tp = myContext.getTipiComponentByPath(pathVal);
			if (tp != null) {
				// System.err.println("ATTEMPTING TO DISPOSE: " + tp.getPath());
			} else {
				System.err.println("ATTEMPTING TO DISPOSE NULL component. ");
			}
			// TipiPathParser tp = new TipiPathParser( myComponent, myContext,
			// path);
			myContext.disposeTipiComponent(tp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
