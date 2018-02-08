package com.dexels.navajo.functions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class DateAppendClockTime extends FunctionInterface {

    @Override
	public String remarks() {
        return "Returns a date object containing the current ClockTime";
    }

    @Override
	public String usage() {
        return "DateTime(Date date, ClockTime cTime | String timePattern)";
    }

    @SuppressWarnings("deprecation")
	@Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    	
    		java.util.Date date;
    		ClockTime cTime;
    		
		if(this.getOperands().size() != 2 ){
			throw new TMLExpressionException(this, "error: arguements missing. Please provide a valid Date and a valid ClockTime/String object.");
		}else {
			if(!(this.getOperand(0) instanceof java.util.Date || this.getOperand(0) instanceof Date))
				throw new TMLExpressionException(this, "error: arguement 0 must be a Date object. The object you supplied is invalid.");
			date = (java.util.Date) this.getOperands().get(0); 
			
			if(!(this.getOperand(1) instanceof ClockTime || this.getOperand(1) instanceof String))
				throw new TMLExpressionException(this, "error: argument 1 must be a ClockTime object or a String with format hh:mm. The object you supplied is invalid. ");
			if(this.getOperand(1) instanceof String) {
				String o = this.getOperand(1).toString();
				if(!o.matches("(?:[0-1][1-9]|2[0-4]):[0-5]\\d")) {
					throw new TMLExpressionException(this, "error: argument 1 must be a ClockTime object or a String with format hh:mm. The format you supplied is wrong.");
				}
				cTime = (ClockTime) new ClockTime(this.getOperand(1).toString());
			}else
				cTime = (ClockTime) this.getOperand(1);
		}

		date.setSeconds(cTime.getSeconds());
		date.setHours(cTime.getHours());
		date.setMinutes(cTime.getMinutes());
		
		return date;
    }

    public static void main(String[] args) throws Exception {
    		
    		DateAppendClockTime d = new DateAppendClockTime();
    		java.util.Date date = new java.util.Date();
    		ClockTime cTime = new ClockTime("11:28");
    		
    		/* Test 1 :: Valid Parameters*/
    		try {
    			System.out.println(" ------ Running Valid Parameters case ------ ");
			System.out.println("Sending date :: " + date);
			System.out.println("Sending ClockTime :: " + cTime);
			d.reset();
			d.insertOperand(date);
			d.insertOperand(cTime);
			System.out.println(d.evaluate());
			System.out.println("All good :) ");
		} catch (Exception e) {
			e.printStackTrace();
		} 
    		
    		/* Test 1.2 :: Valid Parameters */
    		try {
    			System.out.println(" ------ Running Valid Parameters case ------ ");
			System.out.println("Sending date :: " + date);
			System.out.println("Sending String as Time :: " + "22:22");
			d.reset();
			d.insertOperand(date);
			d.insertOperand("22:22");
			System.out.println(d.evaluate());
			System.out.println("All good :) ");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    		/* Test 2 :: Invalid Parameters */
    		try {
    			System.out.println(" ------ Running invalid Parameters case ------ ");
    			System.out.println("Sending 0 arguments");
    			d.reset();
			System.out.println(d.evaluate());
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.out.println("All good :) ");
    		} 
    		
    		/* Test 2.2 :: Invalid Parameters */
    		try {
    			System.out.println(" ------ Running invalid Parameters case ------ ");
    			System.out.println("Sending date :: " + date);
    			System.out.println("Sending time 22:99");
    			d.reset();
    			d.insertOperand(date);
    			d.insertOperand("22:99");
			System.out.println(d.evaluate());
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.out.println("All good :) ");
    		} 
	    	
    }
}