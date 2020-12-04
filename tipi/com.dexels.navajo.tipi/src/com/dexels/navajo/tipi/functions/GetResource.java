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

/**
 * @author frank
 * 
 * 
 */
public class GetResource extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Retrieves the contents of a resource";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "GetResource(Context,name)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() == 0) {
			throw new TMLExpressionException(this,
					"GetResource NEEDS arguments");
		}

		Object pp = getOperand(0);
		if (pp == null) {
			throw new TMLExpressionException(this,
					"Invalid operand: null context ");
		}
		if (pp instanceof TipiContext) {
			TipiContext tt = (TipiContext) pp;

			String name = (String) getOperand(1);
			return tt.getResourceURL(name);
		}
		throw new TMLExpressionException(this, "Invalid operand: "
				+ pp.getClass().getName());

	}

}
