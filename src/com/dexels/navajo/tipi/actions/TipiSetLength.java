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
public class TipiSetLength extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand o = getEvaluatedParameter("property", event);
		Operand q = getEvaluatedParameter("length", event);
		Property p = (Property)o.value;
		if (p != null) {
			p.setLength((Integer) q.value);
		} else {
			throw new TipiException("Property is NULL!");
		}
	}
}
