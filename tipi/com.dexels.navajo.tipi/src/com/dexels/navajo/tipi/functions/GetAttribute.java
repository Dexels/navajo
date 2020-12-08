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
import com.dexels.navajo.tipi.TipiComponent;

/**
 * @author frank
 * 
 */
public class GetAttribute extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Gets the attribute ref of a component";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "GetAttribute(TipiComponent source, String attribute)";
	}

	// GetComponent({component://init/desktop},{event:/from})
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			return null;
		}
		if (!(pp instanceof TipiComponent)) {
			throw new TMLExpressionException(this, "Invalid operand: "
					+ pp.getClass().getName());
		}
		Object o = getOperand(1);
		if (o == null) {
			return null;
		}
		if (!(o instanceof String)) {
			throw new TMLExpressionException(this, "Invalid operand: "
					+ o.getClass().getName());
		}
		TipiComponent tc = (TipiComponent) pp;
		String name = (String) o;
		return tc.getAttributeRef(name);
	}

}
