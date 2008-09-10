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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Resolves a navajo name. Source component is required to resolve context data.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetNavajo(TipiContext context, String navajoName)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			throw new TMLExpressionException(this, "Invalid operand: null context ");
		}
		if (!(pp instanceof TipiContext)) {
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
		TipiContext tc = (TipiContext) pp;
		String path = (String) o;
		return tc.getNavajo(path);
	}

}
