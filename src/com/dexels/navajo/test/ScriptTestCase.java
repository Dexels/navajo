package com.dexels.navajo.test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

import junit.framework.TestCase;

public abstract class ScriptTestCase extends TestCase {
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
	
	


	
	@Override
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
		
		super.setUp();
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
		if(skipAllTests) {
			return;
		}
		Message message = getResultNavajo().getMessage("error");
		if(message!=null) {
			try {
				message.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		}
		if(message!=null) {
			fail("Error message found: "+getPropertyValue("error/message"));		
		}
		
	}
	protected final Navajo getResultNavajo() {
		return ScriptTestContext.getInstance().getScriptResult(getScriptName());
	}

	public void testConditionErrors() {
		if(skipAllTests) {
			return;
		}

		Message message = getResultNavajo().getMessage("ConditionErrors");
		if(message!=null) {
			try {
				message.write(System.err);
			} catch (NavajoException e) {
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
		if(skipAllTests) {
			return;
		}

		Message message = getResultNavajo().getMessage("AuthorizationError");
		if(message!=null) {
			try {
				message.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
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
	
	public final void assertMessageExists(Navajo input, String messageName) {
		assertTrue(messageExists(input, messageName));
	}
	
	public final void assertMessageNotExists(String messageName) {
		assertFalse(messageExists(messageName));
	}	
	
	public final void assertMessageNotExists(Navajo input, String messageName) {
		assertFalse(messageExists(input, messageName));
	}	
	
	public final boolean messageExists(String messageName) {
		Message m = getResultNavajo().getMessage(messageName);
		return m!=null;
	}
	
	public final boolean messageExists(Navajo input, String messageName) {
		Message m = input.getMessage(messageName);
		return m!=null;
	}

	public final void assertPropertyExists(String propertyName) {
		assertTrue(propertyExists(propertyName));
	}
	
	public final void assertPropertyExists(Navajo input, String propertyName) {
		assertTrue(propertyExists(input, propertyName));
	}

	public final void assertPropertyNotExists(String propertyName) {
		assertFalse(propertyExists(propertyName));
	}

	public final void assertPropertyNotExists(Navajo input, String propertyName) {
		assertFalse(propertyExists(input, propertyName));
	}
	
	public final boolean propertyExists(String propertyName) {
		Property m = getResultNavajo().getProperty(propertyName);
		return m!=null;
	}
	
	public final boolean propertyExists(Navajo input, String propertyName) {
		Property m = input.getProperty(propertyName);
		return m!=null;
	}

	public final void assertPropertyValueEquals(String propertyName,Object value) {
		assertPropertyExists(propertyName);
		Property m = getResultNavajo().getProperty(propertyName);
		assertEquals(m.getTypedValue(), value);
	}
	
	public final void assertPropertyValueEquals(Navajo input, String propertyName,Object value) {
		assertPropertyExists(input, propertyName);
		Property m = input.getProperty(propertyName);
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
		try {
			getResultNavajo().write(System.err);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}
	
	protected final Message addMessage(Navajo n, String path) {
		return addMessage(n,null,path);
	}
	
	protected final Property addProperty(Navajo n, String path, Object value) {
		if(path.indexOf("/")==-1) {
			fail("Illegal path: "+path);
		}
		String messagePath = path.substring(0, path.lastIndexOf("/"));
		Message parent = addMessage(n, messagePath);
		assertNotNull(parent);
		String propertyName = path.substring(path.lastIndexOf("/")+1,path.length());
		
		Property p;
		try {
			p = NavajoFactory.getInstance().createProperty(n, propertyName, Property.STRING_PROPERTY,"",99,"",Property.DIR_IN);
			parent.addProperty(p);
			p.setAnyValue(value);
			return p;
		} catch (NavajoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		return null;
	}
	private final Message addMessage(Navajo n, Message parent, String path) {
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		Message m = null;
		if(parent!=null) {
			m = parent.getMessage(path);
		} else {
			m = n.getMessage(path);
		}
		if(m!=null) {
			return m;
		}
		if(path.indexOf("/")==-1) {
			Message mm =  NavajoFactory.getInstance().createMessage(n, path);
			if(parent==null) {
				try {
					n.addMessage(mm);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			} else {
				parent.addMessage(mm);
			}
			return mm;
		}
		String parentPath = path.substring(0, path.lastIndexOf("/"));
		String messageName = path.substring(path.lastIndexOf("/")+1,path.length());
		
		Message parentResult = addMessage(n, parent, parentPath);
		
		if(parentResult==null) {
			return null;
		} else {
			return addMessage(n, parentResult,messageName);
		}
		
	}
	
	public final void dump(Navajo n) {
		try {
			n.write(System.err);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}
	
	public final Navajo createNavajo() {
		return NavajoFactory.getInstance().createNavajo();
	}
}
