/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions.test;


import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

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
		da.insertDateOperand(d);
		da.insertIntegerOperand(Integer.valueOf(100));
		da.insertStringOperand("YEAR");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertDateOperand((java.util.Date)o);
		da.insertIntegerOperand(Integer.valueOf(-100));
		da.insertStringOperand("YEAR");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
	}
	
@Test

	public void testWeek() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertDateOperand(d);
		da.insertIntegerOperand(Integer.valueOf(1));
		da.insertStringOperand("WEEK");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertDateOperand((java.util.Date)o);
		da.insertIntegerOperand(Integer.valueOf(-1));
		da.insertStringOperand("WEEK");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
		
		da.reset();
		da.insertDateOperand((java.util.Date)o2);
		da.insertIntegerOperand(Integer.valueOf(7));
		da.insertStringOperand("DAY");
		Object o3 = da.evaluate();
		
		assertEquals(o3, o);
	}
	
@Test
	public void testMonth() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertDateOperand(d);
		da.insertIntegerOperand(Integer.valueOf(100));
		da.insertStringOperand("MONTH");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertDateOperand((java.util.Date)o);
		da.insertIntegerOperand(Integer.valueOf(-100));
		da.insertStringOperand("MONTH");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
	}
	
@Test
	public void testDay() throws Exception {
		da.reset();
		
		Date d = new java.util.Date();
		da.insertDateOperand(d);
		da.insertIntegerOperand(Integer.valueOf(100));
		da.insertStringOperand("DAY");
		Object o = da.evaluate();
		assertEquals(o.getClass(), java.util.Date.class);
		
		da.reset();
		da.insertDateOperand((java.util.Date)o);
		da.insertIntegerOperand(Integer.valueOf(-100));
		da.insertStringOperand("DAY");
		Object o2 = da.evaluate();
		
		assertEquals(o2, d);
	}

}
