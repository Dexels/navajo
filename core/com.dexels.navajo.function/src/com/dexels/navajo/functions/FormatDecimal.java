/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 13, 2005
 *
 */
package com.dexels.navajo.functions;

import java.text.DecimalFormat;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

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
