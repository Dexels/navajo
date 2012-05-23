package com.dexels.navajo.functions.test;

import org.junit.After;
import org.junit.Before;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import static org.junit.Assert.*;

@SuppressWarnings("unused")

public class Switch extends AbstractFunction {

	protected FunctionFactoryInterface fff;
	protected ClassLoader cl;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fff = (FunctionFactoryInterface) FunctionFactoryFactory.getInstance();
		cl = getClass().getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	protected Navajo createTestNavajo() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.setType(Message.MSG_TYPE_ARRAY);
		Message array1 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array1);
		doc.addMessage(array);
		Property p = NavajoFactory.getInstance().createProperty(doc, "Noot", Property.INTEGER_PROPERTY, "10", 10, "", "in");
		p.setValue(10);
		array1.addProperty(p);
		
		
		Message single = NavajoFactory.getInstance().createMessage(doc, "Single");
		doc.addMessage(single);
		Property p2 = NavajoFactory.getInstance().createProperty(doc, "Selectie", "1", "", "in");
		p2.addSelection(NavajoFactory.getInstance().createSelection(doc, "key", "value", true));
		single.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(doc, "Vuur", Property.INTEGER_PROPERTY, "10", 10, "", "out");
		p3.setValue(10);
		single.addProperty(p3);
		
		return doc;
	}
	
	
	public void testSingleSimpleHit() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
	
		fi.insertOperand(10);
		fi.insertOperand(10);
		fi.insertOperand(-10);
		fi.insertOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), (Integer) result);
		
	}
	
	public void testSingleSimpleMiss() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
	
		fi.insertOperand(Expression.evaluate("20", null).value);
		fi.insertOperand(Expression.evaluate("10", null).value);
		fi.insertOperand(Expression.evaluate("-10", null).value);
		fi.insertOperand(Expression.evaluate("0", null).value);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(0), (Integer) result);
		
	}
	
	public void testIntegerSingleHit() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		// Switch('/Single/Vuur', 10, -10, 0). If property /Single/Vuur has value 10 it becomes -10 else it becomes 0.
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertOperand(10);
		fi.insertOperand(-10);
		fi.insertOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), (Integer) result);
		
	}
	
	
	public void testIntegerMultipleHit() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		// Switch('/Single/Vuur', 10, -10, 0). If property /Single/Vuur has value 10 it becomes -10 else it becomes 0.
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertOperand(20);
		fi.insertOperand(-20);
		fi.insertOperand(10);
		fi.insertOperand(-10);
		fi.insertOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), (Integer) result);
		
	}
	
	public void testIntegerMultipleMiss() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		// Switch('/Single/Vuur', 10, -10, 0). If property /Single/Vuur has value 10 it becomes -10 else it becomes 0.
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertOperand(20);
		fi.insertOperand(-20);
		fi.insertOperand(15);
		fi.insertOperand(-10);
		fi.insertOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(0), (Integer) result);
		
	}
	
	public void testIntegerMultipleHitInArrayMessage() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		
		fi.insertOperand(Expression.evaluate("[Noot]", n, null, n.getMessage("Aap").getElements().get(0)).value);
		fi.insertOperand(20);
		fi.insertOperand(-20);
		fi.insertOperand(10);
		fi.insertOperand(-10);
		fi.insertOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), (Integer) result);
		
	}
	
	public void testIntegerMultipleMissInArrayMessage() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		
		fi.insertOperand(Expression.evaluate("[Noot]", n, null, n.getMessage("Aap").getElements().get(0)).value);
		fi.insertOperand(20);
		fi.insertOperand(-20);
		fi.insertOperand(15);
		fi.insertOperand(-15);
		fi.insertOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(0), (Integer) result);
		
	}
	
	public void testWithUnknownProperty() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		try {
		fi.insertOperand(Expression.evaluate("[/Single/Kibbeling]", n).value);
		fi.insertOperand(20);
		fi.insertOperand(-20);
		fi.insertOperand(15);
		fi.insertOperand(-10);
		fi.insertOperand(0);
		
		Object result = fi.evaluate();
		} catch (TMLExpressionException tmle) {
			assertTrue(tmle.getMessage().indexOf("/Single/Kibbeling") != -1);
		}
	}
	
	public void testWithNotEnoughParameters() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertOperand(20);
		fi.insertOperand(-20);
		try {
		Object result = fi.evaluate();
		} catch (TMLExpressionException tmle) {
			assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
		}
	}
	
	public void testWithNotZeroParameters() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		
		
		try {
			Object result = fi.evaluate();
			} catch (TMLExpressionException tmle) {
				assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
			}
	
	}
	
	public void testWithOnlyPropertyParameter() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n).value);
		
		try {
			Object result = fi.evaluate();
			} catch (TMLExpressionException tmle) {
				assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
			}
	
	   
	}
	
	public void testWithPropertyAndSingleValueParameter() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertOperand(0);
		
		try {
			Object result = fi.evaluate();
			} catch (TMLExpressionException tmle) {
				assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
			}
	
	}
}
