/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiContext;

/**
 * @author frank
 * 
 */
public class GetGlobal extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GetGlobal.class);
	
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
			logger.warn("Expected string param, found: " + pp.getClass());
		}
		throw new TMLExpressionException(this, "Invalid operand: "
				+ pp.getClass().getName());

	}

}
