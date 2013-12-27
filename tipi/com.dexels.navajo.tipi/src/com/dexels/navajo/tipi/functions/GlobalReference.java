/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.GlobalRef;

/**
 * @author frank
 * 
 */
public class GlobalReference extends FunctionInterface {

	@Override
	public String remarks() {
		return "GlobalReference to a certain global";
	}

	@Override
	public String usage() {
		return "GlobalReference(context,'name')";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this,
					"Invalid number of operands: " + getOperands() + " usage: "
							+ usage());

		}

		TipiContext context = (TipiContext) getOperand(0);
		Object pp = getOperand(1);

		if (pp instanceof String) {
			String ss = (String) pp;

			return new GlobalRef(ss, context);

		}
		throw new TMLExpressionException(this, "Invalid operand: "
				+ pp.getClass().getName());

	}

}
