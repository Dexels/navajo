/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * @author frank
 * 
 */
public class ToUnderline extends FunctionInterface {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#remarks()
     */
    @Override
	public String remarks() {
        return "Make text underlined. Html-style (depending on 2nd parameter including html tag)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#usage()
     */
    @Override
	public String usage() {
        return "ToUnderline(string, boolean|empty)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
     */
    @Override
	public Object evaluate() throws TMLExpressionException {
        Object pp = getOperand(0);
        String result = null;

        if (!(pp instanceof String)) {
            throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
        } else {
            result = "<html><u>" + pp + "</u></html>";
            if (getOperands().size() > 1) {
                if (getOperand(1) instanceof Boolean && (Boolean)getOperand(1)) {
                    result = "<u>" + pp + "</u>";
                }
            }
        }
        return result;
    }
    
    @Override
	public boolean isPure() {
    		return false;
    }

}
