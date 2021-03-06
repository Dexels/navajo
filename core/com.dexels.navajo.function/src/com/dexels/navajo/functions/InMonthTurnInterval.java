/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class InMonthTurnInterval extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		int size = this.getOperands().size();
		if(size != 3){
			throw new TMLExpressionException("Illegal number of arguments for function InMonthTurnInterval(): " + size);
		}
		Object date = this.getOperands().get(0);
    Object days = this.getOperands().get(1);
    Object forward = this.getOperands().get(2);
    
    java.util.Date dt;
    int  ds;
    boolean fw;
    
    if (date instanceof java.util.Date){
    	dt = (java.util.Date)date;
    }else{
    	throw new TMLExpressionException("Illegal argument type for date InMonthTurnInterval(): " + date.getClass().getName());
    }
    if (days instanceof Integer){
    	ds = ((Integer)days).intValue();	
    }else{
    	throw new TMLExpressionException("Illegal argument type for days InMonthTurnInterval(): " + days.getClass().getName());
    }
    if (forward instanceof java.lang.Boolean){
    	fw = ((java.lang.Boolean)forward).booleanValue();	
    }else{
    	throw new TMLExpressionException("Illegal argument type for first InMonthTurnInterval(): " + forward.getClass().getName());
    }
    
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    int dim = c.get(Calendar.DAY_OF_MONTH);
    
    if(fw){	
    	if(dim <= ds){
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }else{
    	int max = c.getMaximum(Calendar.DAY_OF_MONTH) - ds;
    	
    	if(dim > max){
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }
	}


	@Override
	public String remarks() {
		return "Returns true when a given date is either in the first or last n days of the month";
	}


	@Override
	public String usage() {
		return "Usage: InMonthTurnInterval([(Date)date], [(Integer)days], [(Boolean)first]) ";
	}
	
	public static void main(String[] args) throws TMLExpressionException, ParseException{
			InMonthTurnInterval function = new InMonthTurnInterval();
			function.reset();
			SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyy");
			java.util.Date d = sdf.parse("21122006");
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			function.insertDateOperand(c.getTime());
			function.insertIntegerOperand(Integer.valueOf(22));
			function.insertBooleanOperand(Boolean.TRUE);
		
			Object result = function.evaluate();
			if(result instanceof java.lang.Boolean){
				System.err.println("Result: " + ((java.lang.Boolean)result).booleanValue());
			}
	}

}
