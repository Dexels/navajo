package com.dexels.navajo.functions.test;


import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Generated code for the test suite <b>DateAdd</b> located at
 * <i>/NavajoFunctions/testsrc/DateAdd.testsuite</i>.
 */
public class DateAdd  {
	/**
	 * Constructor for DateAdd.
	 * @param name
	 */
	
	com.dexels.navajo.functions.DateAdd da = new com.dexels.navajo.functions.DateAdd();
	



@Test
	public void testYear() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertOperand(d);
		da.insertOperand(new Integer(100));
		da.insertOperand("YEAR");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertOperand(o);
		da.insertOperand(new Integer(-100));
		da.insertOperand("YEAR");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
	}
	
@Test

	public void testWeek() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertOperand(d);
		da.insertOperand(new Integer(1));
		da.insertOperand("WEEK");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertOperand(o);
		da.insertOperand(new Integer(-1));
		da.insertOperand("WEEK");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
		
		da.reset();
		da.insertOperand(o2);
		da.insertOperand(new Integer(7));
		da.insertOperand("DAY");
		Object o3 = da.evaluate();
		
		assertEquals(o3, o);
	}
	
@Test
	public void testMonth() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertOperand(d);
		da.insertOperand(new Integer(100));
		da.insertOperand("MONTH");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertOperand(o);
		da.insertOperand(new Integer(-100));
		da.insertOperand("MONTH");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
	}
	
@Test
	public void testDay() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertOperand(d);
		da.insertOperand(new Integer(100));
		da.insertOperand("DAY");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertOperand(o);
		da.insertOperand(new Integer(-100));
		da.insertOperand("DAY");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
	}

}
