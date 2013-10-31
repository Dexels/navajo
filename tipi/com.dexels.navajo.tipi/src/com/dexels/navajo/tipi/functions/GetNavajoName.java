/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
