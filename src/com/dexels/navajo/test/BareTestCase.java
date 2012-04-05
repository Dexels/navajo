package com.dexels.navajo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public abstract class BareTestCase  {
	
	/**
	 * Asserts that a condition is true. If it isn't it throws
	 * an AssertionFailedError.
	 */
	
	public final void assertMessageExists(Navajo input, String messageName) {
		assertTrue(messageExists(input, messageName));
	}
		
	public final void assertMessageNotExists(Navajo input, String messageName) {
		assertFalse(messageExists(input, messageName));
	}	
	
	public final boolean messageExists(Navajo input, String messageName) {
		Message m = input.getMessage(messageName);
		return m!=null;
	}
	
	public final void assertPropertyExists(Navajo input, String propertyName) {
		assertTrue(propertyExists(input, propertyName));
	}

	public final void assertPropertyNotExists(Navajo input, String propertyName) {
		assertFalse(propertyExists(input, propertyName));
	}
		
	public final boolean propertyExists(Navajo input, String propertyName) {
		Property m = input.getProperty(propertyName);
		return m!=null;
	}

	public final void assertPropertyValueEquals(Navajo input, String propertyName,Object value) {
		assertPropertyExists(input, propertyName);
		Property m = input.getProperty(propertyName);
		assertEquals(m.getTypedValue(), value);
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
