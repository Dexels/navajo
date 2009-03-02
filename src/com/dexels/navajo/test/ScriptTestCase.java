package com.dexels.navajo.test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

import junit.framework.TestCase;

public abstract class ScriptTestCase extends TestCase {


	public String getInputName() {
		return null;
	}

	public String getScriptName() {
		return getClass().getName().replaceAll("\\.", "/");
	}
	
	public abstract void testResult();
	


	
	@Override
	protected void setUp() throws Exception {
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
		
		super.setUp();
	}

	/**
	 * Alternative to named input. For example, you can call another service,
	 * or construct a Navajo object in another way.
	 * @return
	 */
	protected Navajo acquireInput() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * All input will be passed through this method before being sent to the server
	 * If you override acquireInput, it does not matter if you 'prepare' there or
	 * override this method too.
	 * 
	 * @param input
	 */
	private void prepareInput(Navajo input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
//	public void testConnection() throws Throwable {

//		testError(result);
//		testAuthorizationErrors(result);
//		testConditionErrors(result);
//		testResult();
//	}

	
	public void testError() {
		Message message = getResultNavajo().getMessage("error");
		if(message!=null) {
			try {
				message.write(System.err);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(message!=null) {
			fail("Error message found: "+getPropertyValue("error/message"));		
		}
		
	}
	protected Navajo getResultNavajo() {
		return ScriptTestContext.getInstance().getScriptResult(getScriptName());
	}

	public void testConditionErrors() {
		Message message = getResultNavajo().getMessage("ConditionErrors");
		if(message!=null) {
			try {
				message.write(System.err);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Message element : message.getAllMessages()) {
//				super.
			}
			fail("Condition error found: "+getPropertyValue("ConditionErrors@0/Description")+"\nFailedExpression: "+getPropertyValue("ConditionErrors@0/FailedExpression")+"\n");
		}
//		assertNull(message);
	}

	public void testAuthorizationErrors() {
		Message message = getResultNavajo().getMessage("AuthorizationError");
		if(message!=null) {
			try {
				message.write(System.err);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertNull(message);
		
	}


	/**
	 * Asserts that a condition is true. If it isn't it throws
	 * an AssertionFailedError.
	 */
	public void assertMessageExists(String messageName) {
		assertTrue(messageExists(messageName));
	}
	
	public void assertMessageNotExists(String messageName) {
		assertFalse(messageExists(messageName));
	}	
	
	public boolean messageExists(String messageName) {
		Message m = getResultNavajo().getMessage(messageName);
		return m!=null;
	}

	public void assertPropertyExists(String propertyName) {
		assertTrue(propertyExists(propertyName));
	}

	public void assertPropertyNotExists(String propertyName) {
		assertFalse(propertyExists(propertyName));
	}

	
	public boolean propertyExists(String propertyName) {
		Property m = getResultNavajo().getProperty(propertyName);
		return m!=null;
	}

	public void assertPropertyValueEquals(String propertyName,Object value) {
		assertPropertyExists(propertyName);
		Property m = getResultNavajo().getProperty(propertyName);
		assertEquals(m.getTypedValue(), value);
	}
	
	public Object getPropertyValue(String path) {
		Property p = getResultNavajo().getProperty(path);
		if(p!=null) {
			return p.getTypedValue();
		}
		throw new AssertionError("Property: "+path+" missing from result!");
	}

	public void dumpResult() throws NavajoException {
		getResultNavajo().write(System.err);
	}
	
}
