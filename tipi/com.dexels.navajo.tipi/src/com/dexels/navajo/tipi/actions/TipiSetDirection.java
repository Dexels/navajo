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
public class TipiSetDirection extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7414085913763797333L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand o = getEvaluatedParameter("property", event);
		Operand q = getEvaluatedParameter("direction", event);
		Property p = (Property) o.value;
		if (p != null) {
			p.setDirection((String) q.value);
		} else {
			throw new TipiException("Property is NULL!");
		}
	}
}
