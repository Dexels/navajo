package com.dexels.navajo.tipi.actions;


import com.dexels.navajo.document.Property;
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
public final class TipiSetPropertySubType extends TipiAction {

	private static final long serialVersionUID = 472131866710083014L;

	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// Set<String> ss = getParameterNames();

		Object p = getEvaluatedParameterValue("property", event);
		Object k = getEvaluatedParameterValue("key", event);
		Object v = getEvaluatedParameterValue("value", event);
		if (p == null) {
			throw new TipiException(
					"TipiSetPropertySubType: property missing ");

		}
		if (!(p instanceof Property)) {
			throw new TipiException(
					"TipiSetPropertySubType: property wrong type");
		}
		if (k == null) {
			throw new TipiException(
					"TipiSetPropertySubType: key missing ");

		}
		if (!(k instanceof String)) {
			throw new TipiException(
					"TipiSetPropertySubType: key wrong type");
		}
		if (v!= null && !(v instanceof String)) {
			throw new TipiException(
					"TipiSetPropertySubType: value wrong type");
		}
		Property prop = (Property) p;
		prop.addSubType((String) k, (String) v);
		
	}
	
}
