package com.dexels.navajo.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.script.api.SystemException;

public class ExpressionTest {

	private Navajo testDoc;
	private Message topMessage;
	private Selection testSelection;


	@Before
	public void setup() {
		NavajoFactory.getInstance().setExpressionEvaluator(
				new CachedExpressionEvaluator());
		
		testDoc = NavajoFactory.getInstance().createNavajo();
		topMessage = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
		testDoc.addMessage(topMessage);
		Property pt = NavajoFactory.getInstance().createProperty(testDoc,
				"TopProp", "1", "", Property.DIR_IN);
		testSelection = NavajoFactory.getInstance().createSelection(testDoc, "option1", "value1", true);
		pt.addSelection(testSelection);
		topMessage.addProperty(pt);
		Message a = NavajoFactory.getInstance().createMessage(testDoc,
				"MyArrayMessage", "array");
		topMessage.addMessage(a);
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
	}
	@Test
	public void testExpression() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("1+1", null, null);
		assertEquals(2, o.value);

		o = ee.evaluate("TODAY + 0#0#2#0#0#0", null, null);
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
				testDoc, null);

		assertEquals("hallo:aap1", o.value);

		o = ee.evaluate("'hallo:' + [/MyTop/MyArrayMessage@2/MyProp2]", testDoc, null);

		assertEquals("hallo:aap2", o.value);

	}

	@Test
	public void testExpressionMoney() throws Exception {
		boolean eq = Utils.equals(new Money(), null);
		assertTrue(eq);
	}

	@Test
	public void testUnicode() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("'ø'+'æ'", null, null);
		assertEquals("øæ", o.value);
	}

	@Test
	public void testExpressionNewlineOutside() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("1\n+\n1", null, null);
		assertEquals(2, o.value);
	}

	@Test
	public void testExpressionNewline() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("'aap\nnoot'", null, null);
		assertEquals("aap\nnoot", o.value);
	}
	
	@Test
	public void testNonAscii() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("'àáâãäåāăąæßçćĉċčèéêëēĕėęěĝğġģĥħìíîïĩīĭıįĵķĸĺļľŀłñńņňŋòóôöõøōŏőœŕŗřśŝşšţťŧùúûüũůūŭűųŵýÿŷźżž'+'àáâãäåāăąæßçćĉċčèéêëēĕėęěĝğġģĥħìíîïĩīĭıįĵķĸĺļľŀłñńņňŋòóôöõøōŏőœŕŗřśŝşšţťŧùúûüũůūŭűųŵýÿŷźżž'", null,null);
		assertEquals("àáâãäåāăąæßçćĉċčèéêëēĕėęěĝğġģĥħìíîïĩīĭıįĵķĸĺļľŀłñńņňŋòóôöõøōŏőœŕŗřśŝşšţťŧùúûüũůūŭűųŵýÿŷźżžàáâãäåāăąæßçćĉċčèéêëēĕėęěĝğġģĥħìíîïĩīĭıįĵķĸĺļľŀłñńņňŋòóôöõøōŏőœŕŗřśŝşšţťŧùúûüũůūŭűųŵýÿŷźżž", o.value);
	}
	
	
	@Test
	public void testSelectionExpressions() throws TMLExpressionException, SystemException {
		Navajo testDoc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
		testDoc.addMessage(m);
		Property pt = NavajoFactory.getInstance().createProperty(testDoc,
				"TopProp", "1", "", Property.DIR_IN);
		Selection s = NavajoFactory.getInstance().createSelection(testDoc, "option1", "value1", true);
		pt.addSelection(s);
		m.addProperty(pt);
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
		
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate(
				"'hallo:' + [TopProp:name]",
				testDoc,null,m);

		
//		o = Expression.evaluate(
//				"'hallo:' + [out:/MyTop/MyArrayMessage@MyProp=noot1/MyProp2]",
//				testDoc);
//
		assertEquals("hallo:option1", o.value);
		o = Expression.evaluate("[TopProp:value]",testDoc,null,m);
		assertEquals("value1", o.value);
	}
	
	
	@Test
	public void testExpressionWithDocSpec() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("[custom|/MyTop/MyArrayMessage@2/MyProp2]", testDoc);
		assertEquals("aap2", o.value);
	}

	@Test
	public void testExpressionWithinSelection() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("[name]", testDoc,null,topMessage,testSelection,null);
		assertEquals("option1", o.value);
	}

	@Test
	public void testExpressionWithDocSpecWithinSelection() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("[custom|name]", testDoc,null,topMessage,testSelection,null);
		assertEquals("option1", o.value);
	}

	// TODO: Re-enable if I backport this to non-compiled expressions.
	@Test @Ignore
	public void testExpressionWithDocSpecWithoutCompiled() throws Exception {
		Expression.compileExpressions = false;
		Operand o = Expression.evaluate("[custom|/MyTop/MyArrayMessage@2/MyProp2]", testDoc);
		assertEquals("aap2", o.value);
	}

	@Test
	public void testExpressionWithinSelectionWithoutCompiled() throws Exception {
		Expression.compileExpressions = false;
		Operand o = Expression.evaluate("[name]", testDoc,null,topMessage,testSelection,null);
		assertEquals("option1", o.value);
	}

	// TODO: Re-enable if I backport this to non-compiled expressions.
	@Test @Ignore
	public void testExpressionWithDocSpecWithinSelectionWithoutCompiled() throws Exception {
		Expression.compileExpressions = false;
		Operand o = Expression.evaluate("[custom|name]", testDoc,null,topMessage,testSelection,null);
		assertEquals("option1", o.value);
	}
}
