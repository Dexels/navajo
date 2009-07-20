/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;

/**
 * @author frank
 * 
 */
public class FindComponent extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Looks for a component with the given ID, will look downwards from the current component. It will do a PRE order tree search.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "FindComponent(TipiComponent source, String path)";
	}

	// GetComponent({component://init/desktop},{event:/from})
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			return null;
		}
		if (!(pp instanceof TipiComponent)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
		Object o = getOperand(1);
		if (o == null) {
			return null;
		}
		if (!(o instanceof String)) {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
			}
		TipiComponent tc = (TipiComponent) pp;
		String id = (String) o;
		TipiComponent result = tc.findTipiComponentById(id);
		System.err.println("Found: "+result.toString());
		return result;
	}

}
