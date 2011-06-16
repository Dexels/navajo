/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiContext;

/**
 * @author frank
 * 
 */
public class GetGlobal extends FunctionInterface {

	public String remarks() {
		return "Gets a certain global value";
	}

	public String usage() {
		return "GetGlobal(context,'name')";
	}

	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this,
					"Invalid number of operands: " + getOperands().size()
							+ " usage: " + usage());

		}

		TipiContext context = (TipiContext) getOperand(0);
		Object pp = getOperand(1);

		if (pp instanceof String) {
			String ss = (String) pp;

			return context.getGlobalValue(ss);

		} else {
			System.err.println("huh? param: " + pp.getClass());
		}
		throw new TMLExpressionException(this, "Invalid operand: "
				+ pp.getClass().getName());

	}

}
