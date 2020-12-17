/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	    id.insertIntegerOperand(12);
	    id.insertStringOperand("2018-04-01");
	    id.insertStringOperand("2018-06-01");

		Object o = id.evaluate();
		assertTrue((Boolean)o == false);
	}
	
@Test

	public void testValidInput() throws Exception {
	    id.reset();
	    id.insertStringOperand("2017-05-01");
	    id.insertStringOperand("2017-04-01");
	    id.insertStringOperand("2017-06-01");
	
		Object o = id.evaluate();
		assertTrue((Boolean)o == true);
	}
	
}
