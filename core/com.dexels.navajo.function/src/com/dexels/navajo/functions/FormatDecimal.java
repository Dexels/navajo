/*
 * Created on May 13, 2005
 *
 */
package com.dexels.navajo.functions;

import java.text.DecimalFormat;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author matthijs
 */
public class FormatDecimal extends FunctionInterface {

    /* (non-Javadoc)
     * @see com.dexels.navajo.parser.FunctionInterface#remarks()
     */
    @Override
	public String remarks() {
       return "Formats a number";
    }
    
    @Override
	public boolean isPure() {
    		return true;
    }


    /* (non-Javadoc)
     * @see com.dexels.navajo.parser.FunctionInterface#usage()
     */
    @Override
	public String usage() {
       return "FormatDecimal(Object number, String format)";
    }

    /* (non-Javadoc)
     * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
     */
    @Override
	public Object evaluate() throws TMLExpressionException {
       if (getOperands().size() != 2) {
           throw new TMLExpressionException(this, "Invalid number of arguments");
       }
       Object o1 = getOperand(0);
       Object o2 = getOperand(1);
       if (!(o2 instanceof String) ) {
           throw new TMLExpressionException(this, "Invalid argument: " + o2);
       }
       DecimalFormat df = new DecimalFormat(o2.toString());
       return df.format(o1);
    }
    

}
