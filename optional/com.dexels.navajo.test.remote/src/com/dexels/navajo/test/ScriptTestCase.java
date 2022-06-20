/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;


public abstract class ScriptTestCase extends BareTestCase {
	
	public boolean skipAllTests;
	public abstract void testResult() throws Exception;

	public final boolean isSkipAllTests() {
		return skipAllTests;
	}

	public final void setSkipAllTests(boolean skipAllTests) {
		this.skipAllTests = skipAllTests;
	}

	public  String getInputName() {
		return null;
	}

	public final String getScriptName() {
		return getClass().getName().replaceAll("\\.", "/");
	}
		
	@Before
	protected void setUp() throws Exception {

		if(skipAllTests) {
			return;
		}
		Navajo input = null;
		
		input = acquireInput();
		if(input==null) {
			String inputName = getInputName();
			if(inputName==null) {
				input = NavajoFactory.getInstance().createNavajo();
			} else {
				System.err.println("Input found: "+inputName);
				input = ScriptTestContext.getInstance().getInput(inputName);
			}
		}
		
		prepareInput(input);
		
		String scriptName = getScriptName();
		Navajo result = ScriptTestContext.getInstance().getScriptResult(scriptName);
		if(result==null) {
			result = ScriptTestContext.getInstance().callService(scriptName,input);	
		}
		
	}

	/**
	 * Alternative to named input. For example, you can call another service,
	 * or construct a Navajo object in another way.
	 * @return
	 */
	protected Navajo acquireInput() throws Exception {
		return null;
	}

	/**
	 * All input will be passed through this method before being sent to the server
	 * If you override acquireInput, it does not matter if you 'prepare' there or
	 * override this method too.
	 * 
	 * @param input
	 */
	protected void prepareInput(Navajo input) {
		
	}

	@After
	protected void tearDown() throws Exception {
	}

	
	
//	public void testConnection() throws Throwable {

//		testError(result);
//		testAuthorizationErrors(result);
//		testConditionErrors(result);
//		testResult();
//	}

	@Test
	public void testError() {
		if(skipAllTests) {
			return;
		}
		Message message = getResultNavajo().getMessage("error");
		if(message!=null) {
			message.write(System.err);
		}
		if(message!=null) {
			fail("Error message found: "+getPropertyValue("error/message"));		
		}
		
	}
	@Test
	protected final Navajo getResultNavajo() {
		return ScriptTestContext.getInstance().getScriptResult(getScriptName());
	}

	@Test
	public void testConditionErrors() {
		if(skipAllTests) {
			return;
		}

		Message message = getResultNavajo().getMessage("ConditionErrors");
		if(message!=null) {
			message.write(System.err);
			fail("Condition error found: "+getPropertyValue("ConditionErrors@0/Description")+"\nFailedExpression: "+getPropertyValue("ConditionErrors@0/FailedExpression")+"\n");
		}
//		assertNull(message);
	}

	@Test
	public void testAuthorizationErrors() {
		if(skipAllTests) {
			return;
		}

		Message message = getResultNavajo().getMessage("AuthorizationError");
		if(message!=null) {
			message.write(System.err);
		}
		assertNull(message);
		
	}


	/**
	 * Asserts that a condition is true. If it isn't it throws
	 * an AssertionFailedError.
	 */
	public final void assertMessageExists(String messageName) {
		assertTrue(messageExists(messageName));
	}
	
	public final void assertMessageNotExists(String messageName) {
		assertFalse(messageExists(messageName));
	}	
	
	public final boolean messageExists(String messageName) {
		Message m = getResultNavajo().getMessage(messageName);
		return m!=null;
	}
	
	public final void assertPropertyExists(String propertyName) {
		assertTrue(propertyExists(propertyName));
	}
	
	public final void assertPropertyNotExists(String propertyName) {
		assertFalse(propertyExists(propertyName));
	}

	public final boolean propertyExists(String propertyName) {
		Property m = getResultNavajo().getProperty(propertyName);
		return m!=null;
	}
	
	public final void assertPropertyValueEquals(String propertyName,Object value) {
		assertPropertyExists(propertyName);
		Property m = getResultNavajo().getProperty(propertyName);
		assertEquals(m.getTypedValue(), value);
	}
	
	public final Object getPropertyValue(String path) {
		Property p = getResultNavajo().getProperty(path);
		if(p!=null) {
			return p.getTypedValue();
		}
		throw new AssertionError("Property: "+path+" missing from result!");
	}

	public final void dumpResult()  {
		getResultNavajo().write(System.err);
	}

}
