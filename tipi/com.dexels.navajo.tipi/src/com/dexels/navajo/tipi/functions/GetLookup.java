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
import com.dexels.navajo.tipi.TipiComponent;

/**
 * @author frank
 *
 */
public class GetLookup extends FunctionInterface {


	private final static Logger logger = LoggerFactory
			.getLogger(GetLookup.class);

	@Override
	public String remarks() {
		return "Gets a certain lookup value in the same way as the parser would have, given a certain component";
	}

	@Override
	public String usage() {
		return "GetLookup(component, 'key')";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this,
					"Invalid number of operands: " + getOperands().size()
							+ " usage: " + usage());

		}

		Object cc = getOperand(0);
		Object pp = getOperand(1);
		if (pp == null || cc == null) {
			return null;
		}
		if (!(cc instanceof TipiComponent)) {
			throw new TMLExpressionException(this, "Invalid first operand: "
					+ cc.getClass().getName());
		}
		if (!(pp instanceof String)) {
			throw new TMLExpressionException(this, "Invalid second operand: "
					+ pp.getClass().getName());
		}
		TipiComponent tc = (TipiComponent) cc;
		String ss = (String) pp;
		return tc.getContext().getClassManager().parse(tc, "lookup", ss, null);

	}

}
