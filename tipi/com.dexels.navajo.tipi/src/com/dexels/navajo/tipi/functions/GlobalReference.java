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

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
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
