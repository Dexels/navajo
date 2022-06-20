/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import java.util.Calendar;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public final class DateAdd extends FunctionInterface {

    public DateAdd() {}

    @Override
	public String remarks() {
        return "Get a new Date object where the given field is increased by a given amount. Possible fields are YEAR, MONTH, WEEK and DAY.";
    }

    @Override
	public String usage() {
        return "DateAdd(Date date, Integer amount, String field)";
    }
    
    @Override
	public boolean isPure() {
    		return true;
    }


    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		if (getOperands().size() > 2) {
			java.util.Date datum = (java.util.Date) this.getOperands().get(0);
			Integer arg = (Integer) this.getOperands().get(1);
			String field = (String) this.getOperands().get(2);
			Calendar cal = Calendar.getInstance();
			cal.setTime(datum);
			if (field.equals("YEAR")) {
				cal.add(Calendar.YEAR, arg.intValue());
			} else if (field.equals("MONTH")) {
				cal.add(Calendar.MONTH, arg.intValue());
			} else if (field.equals("DAY")) {
				cal.add(Calendar.DAY_OF_YEAR, arg.intValue());
			} else if (field.equals("WEEK")) {
				cal.add(Calendar.WEEK_OF_YEAR, arg.intValue());
			} else if (field.equals("HOUR")) {
                cal.add(Calendar.HOUR_OF_DAY, arg.intValue());
			} else if (field.equals("MINUTE")) {
                cal.add(Calendar.MINUTE, arg.intValue());
            } else if (field.equals("SECOND")) {
                cal.add(Calendar.SECOND, arg.intValue());
            }
			return cal.getTime();
		}
		java.util.Date datum = (java.util.Date) this.getOperands().get(0);
		ClockTime arg = (ClockTime) this.getOperands().get(1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(datum);
		cal.set(Calendar.HOUR_OF_DAY, arg.getHours());
		cal.set(Calendar.MINUTE, arg.getMinutes());
		cal.set(Calendar.SECOND, arg.getSeconds());
		return cal.getTime();

		
    }
    
    public static void main(String [] args ) throws TMLExpressionException {
    	DateAdd da = new DateAdd();    	
    	Integer add = 365;
        da.reset();
        da.insertDateOperand(new java.util.Date());
    	da.insertIntegerOperand(add);
    	da.insertStringOperand("DAY");
    	Object o = da.evaluate();
    	System.err.println("begindate:  " + new java.util.Date() + " new date: " + o);
    	
    	
    }
}
