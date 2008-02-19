package com.dexels.navajo.tipi.actions;

import java.util.*;

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
public class TipiFireEvent extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		try {
			TipiComponent tp = (TipiComponent) evaluate(getParameter("path").getValue(), event).value;
			if (tp != null) {
				// System.err.println("ATTEMPTING TO DISPOSE: " + tp.getPath());
			} else {
				throw new TipiException("fireEvent: Component not found!");
			}

			String type = (String) evaluate(getParameter("type").getValue(), event).value;
			if (type != null) {
				// System.err.println("ATTEMPTING TO DISPOSE: " + tp.getPath());
			} else {
				throw new TipiException("fireEvent: type parameter not found!");
			}

			Map<String,Object> eventParams = new HashMap<String,Object>();
			Set<String> params = getParameterNames();

			for (Iterator<String> iter = params.iterator(); iter.hasNext();) {
				String element = iter.next();
				if (!element.equals("type") && !element.equals("path")) {
					eventParams.put(element, getParameter(element));
				}
			}

			tp.performTipiEvent(type, eventParams, true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
