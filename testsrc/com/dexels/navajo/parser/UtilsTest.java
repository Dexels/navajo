package com.dexels.navajo.parser;

import java.util.Calendar;


import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSubtractDatesWith24HoursDifference() throws Exception {
		
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		b.add(Calendar.DAY_OF_YEAR, 1);
		
		Object o = com.dexels.navajo.parser.Utils.subtract(a.getTime(), b.getTime());
		
		assertEquals("-1", o+"");
	}
	
	public void testSubtractDatesLessThan24HoursDifference() throws Exception {

		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		b.add(Calendar.DAY_OF_YEAR, 1);
		b.add(Calendar.HOUR_OF_DAY, -3);
		
		Object o = com.dexels.navajo.parser.Utils.subtract(a.getTime(), b.getTime());

		assertEquals("0", o+"");
	}
	
	public void testSubtractDatesMoreThan24HoursDifference() throws Exception {

		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		b.add(Calendar.DAY_OF_YEAR, 1);
		b.add(Calendar.HOUR_OF_DAY, 3);
		
		Object o = com.dexels.navajo.parser.Utils.subtract(a.getTime(), b.getTime());

		assertEquals("-1", o+"");
	}
	
	public void testSubtractDatesInWinterTime() throws Exception {

		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		a.set(2009, 0, 2, 0, 0, 0);
		a.set(Calendar.MILLISECOND, 0);
		b.set(2009, 0, 28, 0, 0, 0);
		b.set(Calendar.MILLISECOND, 0);
		
		System.err.println("a = " + a.getTime());
		System.err.println("b = " + b.getTime());
		
		Integer o = (Integer) com.dexels.navajo.parser.Utils.subtract(a.getTime(), b.getTime());
	
		System.err.println("diff = " + o);
		assertEquals("-26", o+"");
	}
	
	public void testSubtractDatesInWinterAndSummerTime() throws Exception {

		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		a.set(2009, 2, 2, 0, 0, 0);
		a.set(Calendar.MILLISECOND, 0);
		b.set(2009, 2, 30, 0, 0, 0);
		b.set(Calendar.MILLISECOND, 0);
		
		System.err.println("a = " + a.getTime());
		System.err.println("b = " + b.getTime());
		
		Integer o = (Integer) com.dexels.navajo.parser.Utils.subtract(a.getTime(), b.getTime());
	
		System.err.println("diff = " + o);
		
		assertEquals("-28", o+"");
	}
}
