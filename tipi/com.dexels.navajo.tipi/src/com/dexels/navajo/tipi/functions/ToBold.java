/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author frank
 * 
 */
public class ToBold extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Make text bold. Html-style (depending on 2nd parameter including html tag)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "ToBold(string, boolean|empty) duh.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
        String result = null;

		if (!(pp instanceof String)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
        } else {
            result = "<html><b>" + pp + "</b></html>";
            if (getOperands().size() > 1) {
                if (getOperand(1) instanceof Boolean && (Boolean)getOperand(1)) {
                    result = "<b>" + pp + "</b>";
                }
            }
        }
        return result;
	}
}
