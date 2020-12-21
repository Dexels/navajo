/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * @author arjen
 *
 */
public class Abs extends FunctionInterface {

	public Abs() {	
//		super(new Class[][]{ {Float.class,Integer.class, null} });
//		setReturnType(new Class[]{String.class});
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Returns absolute value of a number";
	}

	
	@Override
	public boolean isPure() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = operand(0).value;
		if (o == null) {
			return null;
		}
		
		if (o instanceof Float) {
			return Float.valueOf(Math.abs(((Float) o).floatValue()));
		} else if (o instanceof Double) {
			return Double.valueOf(Math.abs(((Double) o).doubleValue()));
		} else if (o instanceof Integer) {
			return Integer.valueOf(Math.abs(((Integer) o).intValue()));
		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}
	
	public static void main(String [] args) throws Exception {
			
	}
	

}
