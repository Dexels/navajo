/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ToStopwatchTime extends FunctionInterface{

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null)
          return null;

        if (o instanceof Integer){
          return new StopwatchTime( ((Integer) o).intValue());
        }
        
        if (o instanceof java.util.Date){
            return new StopwatchTime( ((java.util.Date) o).getTime());
        }

      return Integer.valueOf(-1);

    }

    @Override
	public String usage() {
        return "ToStopwatchTime(Date|Integer)";
    }

    @Override
	public String remarks() {
        return "Get a stopwatchtime representation of the supplied integer(in milliseconds) or date.";
    }

    @Override
	public boolean isPure() {
    		return false;
    }


	public static void main(String[] args) throws TMLExpressionException {
		ToStopwatchTime ts = new ToStopwatchTime();
		ts.reset();
		ts.insertIntegerOperand(Integer.valueOf(4965234)); // 09:45:08:234
		Object o = ts.evaluate();
		System.err.println("StopwatchTime: " + ((StopwatchTime) o).toString());
		ts.reset();
		ts.insertIntegerOperand(Integer.valueOf(4920000)); // 09:45
		o = ts.evaluate();
		System.err.println("StopwatchTime: " + ((StopwatchTime) o).toString());

	}

}
