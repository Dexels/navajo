/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.tipi.TipiContext;

/**
 * @author frank
 * 
 */
public class GetGlobal extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GetGlobal.class);
	
	@Override
	public String remarks() {
		return "Gets a certain global value";
	}

	@Override
	public String usage() {
		return "GetGlobal(context,'name')";
	}

	@Override
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
