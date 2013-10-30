package com.dexels.navajo.functions;

import java.text.ParseException;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class Trunc extends FunctionInterface {

	@Override
	public String remarks() {
		return "Given a date, a date object without time is returned";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		java.util.Date date = (java.util.Date)this.getOperand(0);
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException ex) {
			throw new TMLExpressionException(this, "Invalid date format: " + date + " for given date pattern: " + "yyyy-MM-dd");
		}
		return date;
	}
	
	@Override
	public String usage() {
	    return "Trunc(Date)";
	}
	
	public static void main(String [] args) throws Exception {
		Trunc t = new Trunc();
		t.reset();
		java.util.Date date = new java.util.Date();
		System.err.println(date);
		t.insertOperand(date);
		System.err.println(t.evaluate());
	  }
}
