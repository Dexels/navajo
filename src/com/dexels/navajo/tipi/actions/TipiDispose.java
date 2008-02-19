package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
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
public class TipiDispose extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		try {
			String pathVal = getParameter("path").getValue();
			System.err.println("Disposing path: "+pathVal);
			TipiComponent tp = (TipiComponent) evaluate(pathVal, event).value;
			if (tp != null) {
				// System.err.println("ATTEMPTING TO DISPOSE: " + tp.getPath());
				myContext.disposeTipiComponent(tp);
			} else {
				System.err.println("ATTEMPTING TO DISPOSE NULL component. ");
			}
			// TipiPathParser tp = new TipiPathParser( myComponent, myContext,
			// path);
			Message m = myContext.getStateNavajo().getMessage(pathVal);
			if(m!=null) {
				System.err.println("Message found!");
				m.write(System.err);
			} else {
				myContext.getStateNavajo().write(System.err);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
