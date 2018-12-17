package com.dexels.navajo.functions.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.Expression;

@SuppressWarnings("unused")

public class Switch extends AbstractFunction {

	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fff = FunctionFactoryFactory.getInstance();
		cl = getClass().getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Override
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
	
	@Test
	public void testSingleSimpleHit() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
	
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(-10);
		fi.insertIntegerOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), result);
		
	}
	
	@Test
	public void testSingleSimpleMiss() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
	
		fi.insertIntegerOperand((Integer) Expression.evaluate("20", null).value);
		fi.insertIntegerOperand((Integer) Expression.evaluate("10", null).value);
		fi.insertIntegerOperand((Integer) Expression.evaluate("-10", null).value);
		fi.insertIntegerOperand((Integer) Expression.evaluate("0", null).value);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(0), result);
		
	}
	
	@Test
	public void testIntegerSingleHit() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		// Switch('/Single/Vuur', 10, -10, 0). If property /Single/Vuur has value 10 it becomes -10 else it becomes 0.
		fi.insertIntegerOperand((Integer) Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(-10);
		fi.insertIntegerOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), result);
		
	}
	
	
	@Test
	public void testIntegerMultipleHit() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		// Switch('/Single/Vuur', 10, -10, 0). If property /Single/Vuur has value 10 it becomes -10 else it becomes 0.
		fi.insertIntegerOperand((Integer) Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(-20);
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(-10);
		fi.insertIntegerOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), result);
		
	}
	
	@Test
	public void testIntegerMultipleMiss() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		// Switch('/Single/Vuur', 10, -10, 0). If property /Single/Vuur has value 10 it becomes -10 else it becomes 0.
		fi.insertIntegerOperand((Integer) Expression.evaluate("[/Single/Vuur]", n).value);
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(-20);
		fi.insertIntegerOperand(15);
		fi.insertIntegerOperand(-10);
		fi.insertIntegerOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(0), result);
		
	}
	
	@Test
	public void testIntegerMultipleHitInArrayMessage() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		
		fi.insertOperand(Operand.ofDynamic(Expression.evaluate("[Noot]", n, null, n.getMessage("Aap").getElements().get(0)).value));
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(-20);
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(-10);
		fi.insertIntegerOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(-10), result);
		
	}
	
	@Test
	public void testIntegerMultipleMissInArrayMessage() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		
		fi.insertOperand(Expression.evaluate("[Noot]", n, null, n.getMessage("Aap").getElements().get(0)));
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(-20);
		fi.insertIntegerOperand(15);
		fi.insertIntegerOperand(-15);
		fi.insertIntegerOperand(0);
		Object result = fi.evaluate();
		assertNotNull(result);
		assertEquals(result.getClass(), Integer.class);
		assertEquals(new Integer(0), result);
		
	}
	
	@Test
	public void testWithUnknownProperty() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		try {
		fi.insertOperand(Expression.evaluate("[/Single/Kibbeling]", n));
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(-20);
		fi.insertIntegerOperand(15);
		fi.insertIntegerOperand(-10);
		fi.insertIntegerOperand(0);
		
		Object result = fi.evaluate();
		} catch (TMLExpressionException tmle) {
			System.err.println("tmle: "+tmle.getMessage());
			assertTrue(tmle.getMessage().indexOf("/Single/Kibbeling") != -1);
		}
	}
	
	@Test
	public void testWithNotEnoughParameters() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n));
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(-20);
		try {
		Object result = fi.evaluate();
		} catch (TMLExpressionException tmle) {
			assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
		}
	}
	
	@Test
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
	
	@Test
	public void testWithOnlyPropertyParameter() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n));
		
		try {
			Object result = fi.evaluate();
			} catch (TMLExpressionException tmle) {
				assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
			}
	
	   
	}
	
	@Test
	public void testWithPropertyAndSingleValueParameter() throws Exception {
		FunctionInterface fi = fff.getInstance(cl, "Switch");
		fi.reset();
		Navajo n = createTestNavajo();
		fi.setInMessage(n);
		fi.insertOperand(Expression.evaluate("[/Single/Vuur]", n));
		fi.insertIntegerOperand(0);
		
		try {
			Object result = fi.evaluate();
			} catch (TMLExpressionException tmle) {
				assertTrue(tmle.getMessage().indexOf("Not enough") != -1);
			}
	
	}
	
	@Test
	public void testUnicodeExpressionFunction() throws Exception {
		Operand result = Expression.evaluate("Unicode('0x20AC')", null,null,null);
		System.err.println("Result:"+result.value);
	}	
	
	@Test
	public void testDirectUnicodeExpressionFunction() throws Exception {
		Operand result = Expression.evaluate("'€2,29'", null,null,null);
		System.err.println("Result:"+result.value);
	}	
	
}
