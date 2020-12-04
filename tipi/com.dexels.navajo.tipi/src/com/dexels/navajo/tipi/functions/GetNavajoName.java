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

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * @author frank
 * 
 */
public class GetNavajoName extends FunctionInterface {

	@Override
	public String remarks() {
		return "Returns the name of the navajo object.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "GetNavajoName(Navajo): String";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			throw new TMLExpressionException(this,
					"Invalid operand: null context ");
		}
		if (!(pp instanceof Navajo)) {
			throw new TMLExpressionException(this, "Invalid operand: "
					+ pp.getClass().getName());
		}

		Navajo tc = (Navajo) pp;
		Header h = tc.getHeader();
		if (h == null) {
			return null;
		}
		String name = h.getRPCName();
		if (name == null) {
			return null;
		}
		return name;
	}

}
