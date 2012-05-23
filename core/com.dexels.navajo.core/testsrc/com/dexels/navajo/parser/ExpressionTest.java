package com.dexels.navajo.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Money;

public class ExpressionTest {

	@Test
	public void testExpression() throws Exception {
		NavajoFactory.getInstance().setExpressionEvaluator(
				new DefaultExpressionEvaluator());
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("1+1", null);
		assertEquals(2, o.value);

		o = ee.evaluate("TODAY + 0#0#2#0#0#0", null);
		System.err.println(o.value);

		Navajo testDoc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
		testDoc.addMessage(m);
		Message a = NavajoFactory.getInstance().createMessage(testDoc,
				"MyArrayMessage", "array");
		m.addMessage(a);
		for (int i = 0; i < 5; i++) {
			Message a1 = NavajoFactory.getInstance().createMessage(testDoc,
					"MyArrayMessage");
			a.addMessage(a1);
			Property p = NavajoFactory.getInstance().createProperty(testDoc,
					"MyProp", "string", "noot" + i, 0, "", "in");
			a1.addProperty(p);
			Property p2 = NavajoFactory.getInstance().createProperty(testDoc,
					"MyProp2", "string", "aap" + i, 0, "", "in");
			a1.addProperty(p2);
		}

		o = ee.evaluate(
				"'hallo:' + [/MyTop/MyArrayMessage@MyProp=noot1/MyProp2]",
				testDoc);

		assertEquals("hallo:aap1", o.value);

		o = ee.evaluate("'hallo:' + [/MyTop/MyArrayMessage@2/MyProp2]", testDoc);

		assertEquals("hallo:aap2", o.value);

	}

	@Test
	public void testExpressionMoney() throws Exception {
		boolean eq = Utils.equals(new Money(), null);
		assertTrue(eq);
	}

}
