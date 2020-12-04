/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Calendar;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ToMilliseconds extends FunctionInterface{

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperands().get(0);


        if (o == null)
          return null;

        if (o instanceof StopwatchTime){ // ClockTime does not support seconds correctly
        	return Integer.valueOf((int) ( (StopwatchTime) o).getMillis());
        }
        if(o instanceof ClockTime){
        	if (((ClockTime) o).calendarValue()==null) {
				    return Long.valueOf(0);
			    }
        	return Long.valueOf(((ClockTime) o).calendarValue().getTimeInMillis());
        }

       throw new com.dexels.navajo.expression.api.TMLExpressionException(this, "Expected one of: ClockTime, StopwatchTime");

    }
  @Override
	public boolean isPure() {
  		return false;
  }

    @Override
	public String usage() {
        return "ToMilliseconds(StopwatchTime|ClockTime)";
    }

    @Override
	public String remarks() {
        return "Get the supplied time in milliseconds (long).";
    }



  public static void main(String[] args) {
    ToMilliseconds ts = new ToMilliseconds();
    ts.reset();
    System.err.println("Time: " + Calendar.getInstance().getTime());
    ts.insertStopwatchOperand(new StopwatchTime("01:22:45:234"));
    try{
      Object o = ts.evaluate();
      System.err.println("Millis: " + ((Integer)o).intValue());
    }catch(Exception e){
    	e.printStackTrace();
    }

    ts.reset();
    System.err.println("Time: " + Calendar.getInstance().getTime());
    ts.insertClockTimeOperand(new ClockTime("01:22"));
    try{
      Object o = ts.evaluate();
      System.err.println("Millis: " + ((Long)o).longValue());
    }catch(Exception e){
    	e.printStackTrace();
    }


  }

}
