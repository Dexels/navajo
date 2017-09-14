package com.dexels.navajo.functions.test;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Generated code for the test suite <b>IsDateBetween</b> located at
 * <i>/NavajoFunctions/testsrc/IsDateBetween.testsuite</i>.
 */
public class IsDateBetween  {
	/**
	 * Constructor for IsDateBetween.
	 * @param name
	 */
	
	com.dexels.navajo.functions.IsDateBetween id = new com.dexels.navajo.functions.IsDateBetween();
	



@Test
	public void testInvalidInput() throws Exception {
	    id.reset();
	    id.insertOperand(12);
	    id.insertOperand("2017-04-01");
	    id.insertOperand("2017-06-01");

		Object o = id.evaluate();
		assertTrue((Boolean)o == true);
	}
	
@Test

	public void testValidInput() throws Exception {
	    id.reset();
	    id.insertOperand("2017-05-01");
	    id.insertOperand("2017-04-01");
	    id.insertOperand("2017-06-01");
	
		Object o = id.evaluate();
		assertTrue((Boolean)o == true);
	}
	
}
