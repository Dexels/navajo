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
public final class TipiSetValue extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		String path = getParameter("to").getValue();
		String value = getParameter("from").getValue();
		Operand evaluated = evaluate(path, event);
		Operand evaluatedValue = evaluate(value, event);
		if (evaluated == null || evaluated.value==null) {
			throw new TipiException("Error in setValue: to evaluation failed. Expression: "+path+" (from: "+value+")");
		}
		if (evaluatedValue == null) {
			evaluatedValue = new Operand(null, "string", null);
		} else {
			if (evaluated.value instanceof Property) {
				Property p = (Property) evaluated.value;
				p.setAnyValue(evaluatedValue.value);
			} else 
			if (evaluated.value instanceof TipiReference) {
				TipiReference p = (TipiReference) evaluated.value;
				p.setValue(evaluatedValue.value, myComponent);
			} else {
				throw new TipiException("Error in setValue: illegal 'to' parameter. Expression: "+path+" (from: "+value+")");
			}
			
		}
	}
}
