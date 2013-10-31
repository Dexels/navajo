package com.dexels.navajo.functions;

import java.util.Calendar;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.parser.FunctionInterface;

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
public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperands().get(0);


        if (o == null)
          return null;

        if (o instanceof StopwatchTime){ // ClockTime does not support seconds correctly
        	return new Integer((int) ( (StopwatchTime) o).getMillis());
        }
        if(o instanceof ClockTime){
        	if (((ClockTime) o).calendarValue()==null) {
				    return new Long(0);
			    }
        	return new Long(((ClockTime) o).calendarValue().getTimeInMillis());
        }

       throw new com.dexels.navajo.parser.TMLExpressionException(this, "Expected one of: ClockTime, StopwatchTime");

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
    ts.insertOperand(new StopwatchTime("01:22:45:234"));
    try{
      Object o = ts.evaluate();
      System.err.println("Millis: " + ((Integer)o).intValue());
    }catch(Exception e){
    }

    ts.reset();
    System.err.println("Time: " + Calendar.getInstance().getTime());
    ts.insertOperand(new ClockTime("01:22"));
    try{
      Object o = ts.evaluate();
      System.err.println("Millis: " + ((Integer)o).intValue());
    }catch(Exception e){
    }


  }

}
