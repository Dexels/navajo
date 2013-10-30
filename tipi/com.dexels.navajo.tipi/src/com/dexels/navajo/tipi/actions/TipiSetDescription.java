package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
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
public class TipiSetDescription extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6820019237316877300L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand o = getEvaluatedParameter("property", event);
		Operand q = getEvaluatedParameter("description", event);
		Property p = (Property) o.value;
		if (p != null) {
			p.setDescription((String) q.value);
		} else {
			throw new TipiException("Property is NULL!");
		}
	}
}
