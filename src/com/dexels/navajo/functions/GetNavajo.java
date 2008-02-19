/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;

/**
 * @author frank
 *
 */
public class GetNavajo extends FunctionInterface {

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Resolves a navajo name. Source component is required to resolve context data.";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetNavajo(TipiComponent source, String navajoName)";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			return null;
		}
		if(!(pp instanceof TipiComponent)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
		Object o = getOperand(1);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {

		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
		TipiComponent tc = (TipiComponent)pp;
		String path  = (String)o;
		return tc.getContext().getNavajo(path);
	}

}
