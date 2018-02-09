package com.dexels.navajo.functions;

import java.util.Calendar;
import java.util.Date;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class DateAppendClockTime extends FunctionInterface {

	@Override
	public String remarks() {
		return "Returns a date object that combines the two arguements that were provided";
	}

	@Override
	public String usage() {
		return "DateTime(Date date, ClockTime cTime)";
	}

	@Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

		// Arguments number check
		if (this.getOperands().size() != 2) {
			throw new TMLExpressionException(this,
					"error: arguements missing. Please provide a valid Date and a valid ClockTime object.");
		}

		Object arg0 = this.getOperand(0);
		Object arg1 = this.getOperand(1);

		// Arguments type checks
		if (!(arg0 instanceof Date)) {
			throw new TMLExpressionException(this,
					"error: arguement 0 must be a Date object. The argument you supplied is : "
							+ this.getOperand(0).getClass());
		}

		if (!(arg1 instanceof ClockTime)) {
			throw new TMLExpressionException(this,
					"error: argument 1 must be a ClockTime. The argument you supplied is : "
							+ this.getOperand(1).getClass());
		}

		Date date = (Date) arg0;
		ClockTime cTime = (ClockTime) arg1;
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.SECOND, cTime.getSeconds());
		calendar.set(Calendar.MINUTE, cTime.getMinutes());
		calendar.set(Calendar.HOUR, cTime.getHours());

		return calendar.getTime();
	}

	public static void main(String[] args) throws Exception {

		DateAppendClockTime d = new DateAppendClockTime();
		Date date = new java.util.Date();
		ClockTime cTime = new ClockTime("11:28");

		/* Test 1 :: Valid Parameters */
		try {
			System.out.println(" ------ Running Valid Parameters case ------ ");
			System.out.println("Sending Date :: " + date);
			System.out.println("Sending ClockTime :: " + cTime);
			d.reset();
			d.insertOperand(date);
			d.insertOperand(cTime);
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
			System.out.println("Sending Date :: " + date);
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