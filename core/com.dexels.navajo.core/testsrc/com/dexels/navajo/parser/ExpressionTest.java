/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.replication.api.ReplicationMessage.Operation;
import com.dexels.replication.factory.ReplicationFactory;

@SuppressWarnings("unchecked")
public class ExpressionTest {

	private Navajo testDoc;
	private Message topMessage;
	private Selection testSelection;
	private ImmutableMessage immutableMessage;
	private ImmutableMessage paramMessage;


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
		
		Map<String,Object> values = new HashMap<>();
		Map<String,String> types = new HashMap<>();
		values.put("SomeString", "Tralala");
		types.put("SomeString", "string");
		values.put("SomeInteger", 3);
		types.put("SomeInteger", "integer");
		immutableMessage = ReplicationFactory.createReplicationMessage(Optional.empty(),Optional.empty(),Optional.empty(), null, 0, Operation.NONE, Collections.emptyList(), types, values, Collections.emptyMap(), Collections.emptyMap(),Optional.empty(),Optional.empty()).message();
		
		Map<String,Object> valueparams = new HashMap<>();
		Map<String,String> typeparams = new HashMap<>();
		valueparams.put("SomeString", "Tralala2");
		typeparams.put("SomeString", "string");
		valueparams.put("SomeInteger", 4);
		typeparams.put("SomeInteger", "integer");
		paramMessage = ReplicationFactory.createReplicationMessage(Optional.empty(),Optional.empty(),Optional.empty(),null, 0, Operation.NONE, Collections.emptyList(), typeparams, valueparams, Collections.emptyMap(), Collections.emptyMap(),Optional.empty(),Optional.empty()).message();


	}
	@Test
	public void testExpression() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("1+1", null, null,null);
		assertEquals(2, o.value);

		o = ee.evaluate("TODAY + 0#0#2#0#0#0", null, null,null);
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
				testDoc, null,null);

		assertEquals("hallo:aap1", o.value);

		o = ee.evaluate("'hallo:' + [/MyTop/MyArrayMessage@2/MyProp2]", testDoc, null,null);

		assertEquals("hallo:aap2", o.value);

	}

	@Test
	public void testExpressionMoney() throws Exception {
		boolean eq = Utils.equals(new Money(), null,"<unknown>");
		assertTrue(eq);
	}

	@Test
	public void testUnicode() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance().getExpressionEvaluator();
		Operand o = ee.evaluate("'ø'+'æ'", null, null,null);
		assertEquals("øæ", o.value);
	}

	@Test
	public void testExpressionNewlineOutside() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("1\n+\n1", null, null,null);
		assertEquals(2, o.value);
	}

	@Test
	public void testExpressionNewline() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("'aap\nnoot'", null, null,null);
		assertEquals("aap\nnoot", o.value);
	}
	
	@Test
	public void testNonAscii() throws Exception {
		ExpressionEvaluator ee = NavajoFactory.getInstance()
				.getExpressionEvaluator();

		Operand o = ee.evaluate("'àáâãäåāăąæßçćĉċčèéêëēĕėęěĝğġģĥħìíîïĩīĭıįĵķĸĺļľŀłñńņňŋòóôöõøōŏőœŕŗřśŝşšţťŧùúûüũůūŭűųŵýÿŷźżž'+'àáâãäåāăąæßçćĉċčèéêëēĕėęěĝğġģĥħìíîïĩīĭıįĵķĸĺļľŀłñńņňŋòóôöõøōŏőœŕŗřśŝşšţťŧùúûüũůūŭűųŵýÿŷźżž'", null,null,null);
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

	// Re-enable if I backport this to non-compiled expressions.
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

	// Re-enable if I backport this to non-compiled expressions.
	@Test @Ignore
	public void testExpressionWithDocSpecWithinSelectionWithoutCompiled() throws Exception {
		Expression.compileExpressions = false;
		Operand o = Expression.evaluate("[custom|name]", testDoc,null,topMessage,testSelection,null);
		assertEquals("option1", o.value);
	}
	
	@Test
	public void testExpressionWithImmutableMessage() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("[SomeInteger]", testDoc,(MappableTreeNode)null,topMessage,(Message)null,(Selection)null,(TipiLink)null,Collections.emptyMap() ,Optional.of(immutableMessage),Optional.of(paramMessage));
		assertEquals(3, o.value);
	}

	@Test
	public void testExpressionWithImmutableParamMessage() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("[@SomeInteger]", testDoc,(MappableTreeNode)null,topMessage,(Message)null,(Selection)null,(TipiLink)null,Collections.emptyMap() ,Optional.of(immutableMessage),Optional.of(paramMessage));
		assertEquals(4, o.value);
	}

	@Test
	public void testListWithMultipleTypes() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("{1,'a',2+2}", testDoc);
		List<Object> res = (List<Object>) o.value;
		assertEquals(3, res.size());
	}

	@Test
	public void testListWithSingleElement() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("{'a'}", testDoc);
		List<Object> res = (List<Object>) o.value;
		assertEquals(1, res.size());
	}

	@Test
	public void testTipiExpression() throws Exception {
		Expression.compileExpressions = true;
		Operand o = Expression.evaluate("{tipi:/bla}", null,null,null,e->"chicken",null,Optional.empty());
		String s = (String) o.value;
		assertEquals("chicken", s);
	}

	@Test
	public void testImmutableTMLPath() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		ImmutableMessage inner = ImmutableFactory.empty().with("innerint", 3, "integer");
		
		ImmutableMessage combined = outer.withSubMessage("sub", inner);
		Operand o = Expression.evaluateImmutable("[sub/innerint]", null, Optional.of(combined), Optional.empty());
		int s = o.integerValue();
		assertEquals(3, s);
	}

	@Test
	public void testTrailingTMLPath() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		ImmutableMessage inner = ImmutableFactory.empty().with("innerint", 3, "integer");
		
		ImmutableMessage combined = outer.withSubMessage("sub", inner);
		Operand o = Expression.evaluateImmutable("[sub/]", null, Optional.of(combined), Optional.empty());
		ImmutableMessage s = o.immutableMessageValue();
		assertEquals(3, s.value("innerint").get());
	}

	@Test
	public void testTrailingTMLPathParam() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		ImmutableMessage inner = ImmutableFactory.empty().with("innerint", 3, "integer");
		
		ImmutableMessage combined = outer.withSubMessage("sub", inner);
		Operand o = Expression.evaluateImmutable("[@sub/]", null, Optional.empty(), Optional.of(combined));
		ImmutableMessage s = o.immutableMessageValue();
		assertEquals(3, s.value("innerint").get());
	}

	@Test
	public void testEmptyTML() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		Operand o = Expression.evaluateImmutable("[]", null, Optional.of(outer), Optional.empty());
		ImmutableMessage s = o.immutableMessageValue();
		assertEquals(outer, s);
	}
	
	@Test
	public void testEmptyTMLJustSlash() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		Operand o = Expression.evaluateImmutable("[/]", null, Optional.of(outer), Optional.empty());
		ImmutableMessage s = o.immutableMessageValue();
		assertEquals(outer, s);
	}

	@Test
	public void testEmptyTMLParam() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		Operand o = Expression.evaluateImmutable("[@]", null, Optional.empty(), Optional.of(outer));
		ImmutableMessage s = o.immutableMessageValue();
		assertEquals(outer, s);
	}
	
	@Test
	public void testEmptySlashTMLParam() throws Exception {
		Expression.compileExpressions = true;
		ImmutableMessage outer = ImmutableFactory.empty().with("outerint", 1, "integer");
		Operand o = Expression.evaluateImmutable("[/@]", null, Optional.empty(), Optional.of(outer));
		ImmutableMessage s = o.immutableMessageValue();
		assertEquals(outer, s);
	}
}

