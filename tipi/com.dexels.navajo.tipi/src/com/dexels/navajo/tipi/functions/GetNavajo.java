/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;

/**
 * @author frank
 * 
 * 
 */
public class GetNavajo extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Resolves a navajo name. Source component is required.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetNavajo(TipiComponent) OR GetNavajo(Context,name)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() == 0) {
			throw new TMLExpressionException(this, "GetNavajo NEEEDS arguments");
		}

		Object pp = getOperand(0);
		if (pp == null) {
			throw new TMLExpressionException(this,
					"Invalid operand: null context ");
		}
		if (!(pp instanceof TipiComponent)) {
			if (pp instanceof TipiContext) {
				TipiContext tt = (TipiContext) pp;
				String name = (String) getOperand(1);
				return tt.getNavajo(name);
			}
			throw new TMLExpressionException(this, "Invalid operand: "
					+ pp.getClass().getName());
		}

		TipiComponent tc = (TipiComponent) pp;
		return tc.getNavajo();
	}

}
