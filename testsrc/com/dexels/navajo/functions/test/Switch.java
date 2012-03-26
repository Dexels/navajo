package com.dexels.navajo.functions.test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

@SuppressWarnings("unused")

public class Switch extends AbstractFunction {

	
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
