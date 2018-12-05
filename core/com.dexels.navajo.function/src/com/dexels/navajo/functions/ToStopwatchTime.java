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

      return new Integer(-1);

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
		ts.insertOperand(new Integer(4965234)); // 09:45:08:234
		Object o = ts.evaluate();
		System.err.println("StopwatchTime: " + ((StopwatchTime) o).toString());
		ts.reset();
		ts.insertOperand(new Integer(4920000)); // 09:45
		o = ts.evaluate();
		System.err.println("StopwatchTime: " + ((StopwatchTime) o).toString());

	}

}
