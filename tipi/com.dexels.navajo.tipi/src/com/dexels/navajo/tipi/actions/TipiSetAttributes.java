package com.dexels.navajo.tipi.actions;

import java.util.Set;

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
public final class TipiSetAttributes extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 472131866710083014L;

	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// Set<String> ss = getParameterNames();

		Object o = getEvaluatedParameterValue("path", event);
		if (o == null) {
			throw new TipiException(
					"TipiSetAttributes: Path component missing ");

		}
		if (!(o instanceof TipiComponent)) {
			throw new TipiException(
					"TipiSetAttributes: Path component wrong type");
		}
		TipiComponent tc = (TipiComponent) o;
		Set<String> p = getParameterNames();
		for (String name : p) {
			if (name.equals("path")) {
				// Path is reserved
				continue;
			}
			Object oo = getEvaluatedParameterValue(name, event);
			tc.setValue(name, oo);
		}
	}
}
