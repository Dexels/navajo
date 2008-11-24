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
 * This thing is silly. Use the constant: {navajo:/...}
 * @deprecated
 * 
 */
@Deprecated
public class GetNavajo extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Resolves a navajo name. Source component is required.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetNavajo(TipiComponent)";
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
		if (!(pp instanceof TipiComponent)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
	
		TipiComponent tc = (TipiComponent) pp;
		return tc.getNavajo();
	}

}
