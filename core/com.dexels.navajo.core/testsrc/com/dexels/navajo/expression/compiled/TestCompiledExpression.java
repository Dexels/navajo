package com.dexels.navajo.expression.compiled;

import java.io.StringReader;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;
import com.dexels.navajo.script.api.SystemException;

public class TestCompiledExpression {

	
	private Navajo input;


	@Before
	public void setup() {
		input = NavajoFactory.getInstance().createNavajo();
		input.addMessage(NavajoFactory.getInstance().createMessage(input,"TestMessage")).addProperty(NavajoFactory.getInstance().createProperty(input, "TestProperty", Property.STRING_PROPERTY, "TestValue", 99, "TestDescription", Property.DIR_OUT));
		Message createMessage = NavajoFactory.getInstance().createMessage(input,"TestArrayMessageMessage",Message.MSG_TYPE_ARRAY);
		input.addMessage(createMessage);
		Message element1 = NavajoFactory.getInstance().createMessage(input, "TestArrayMessageMessage", Message.MSG_TYPE_ARRAY_ELEMENT);
		element1.addProperty(NavajoFactory.getInstance().createProperty(input,"Property",Property.STRING_PROPERTY,"Prop",99,"",Property.DIR_IN));
		Message element2 = NavajoFactory.getInstance().createMessage(input, "TestArrayMessageMessage", Message.MSG_TYPE_ARRAY_ELEMENT);
		Message params = NavajoFactory.getInstance().createMessage(input, "__parms__");
		params.addProperty(NavajoFactory.getInstance().createProperty(input,"Param",Property.STRING_PROPERTY,"SomeParam",99,"",Property.DIR_IN));
		input.addMessage(params);
		//		element2.addProperty(NavajoFactory.getInstance().createProperty(input,"Property",Property.STRING_PROPERTY,"Prop2",99,"",Property.DIR_IN));
		createMessage.addElement(element1);
		createMessage.addElement(element2);
	}
	@Test
	public void parseIntAddition() throws ParseException, TMLExpressionException {
        ContextExpression ss =  ExpressionCache.getInstance().parse("1+1");
        ContextExpression ss2 =  ExpressionCache.getInstance().parse("1+1");
        System.err.println("ss: "+ss.isLiteral());
        System.err.println("ss2: "+ss2.isLiteral());
        System.err.println("Result: "+ss.apply(null, null, null, null, null, null,null,null));
        Assert.assertEquals(2, ss.apply(null, null, null, null, null, null,null,null));
        Assert.assertEquals(2, ss2.apply(null, null, null, null, null, null,null,null));
        Assert.assertTrue(ss.isLiteral());
        Assert.assertTrue(ss2.isLiteral());

	}
	
	@Test
	public void testParseTml() throws ParseException, TMLExpressionException {
		StringReader sr = new StringReader("[/TestMessage/TestProperty]");
		CompiledParser cp = new CompiledParser(sr);
		cp.Expression();
        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda();
        System.err.println("tml: "+ss.isLiteral());
        System.err.println("TMLVALUE: "+ss.apply(input, null, null, null, null, null,null,null));
        Assert.assertEquals("TestValue", ss.apply(input, null, null, null, null, null,null,null));
        Assert.assertFalse(ss.isLiteral());
	}

	@Test
	public void testParseExistsCheck() throws ParseException, TMLExpressionException, SystemException {
		String clause = "?[/TestMessage/TestProperty] AND [/TestMessage/TestProperty] != ''";
		StringReader sr = new StringReader(clause);
		CompiledParser cp = new CompiledParser(sr);
		Object o = Expression.evaluate(clause,input, null, null, null).value;
		System.err.println("<o>"+o);
		cp.Expression();
        Assert.assertEquals(true, o);
	}
	
	@Test
	public void testParseExistsCheckNotExisting() throws ParseException, TMLExpressionException, SystemException {
		String clause = "?[/TestMessage/TestProperty2] AND [/TestMessage/TestProperty2] != ''";
		StringReader sr = new StringReader(clause);
		CompiledParser cp = new CompiledParser(sr);
		Object o = Expression.evaluate(clause,input, null, null, null).value;
		System.err.println("<o>"+o);
		cp.Expression();
        Assert.assertEquals(false, o);
	}
	
	@Test
	public void parseExpressionLiteral() throws ParseException, TMLExpressionException {
		Object o = ExpressionCache.getInstance().evaluate("FORALL( '/TestArrayMessageMessage', `?[Property]`)", input, null, null, null, null, null,null,null);
        System.err.println("ss: "+o);
//        System.err.println("Result: "+ss.apply(input, null, null, null, null, null, null,null));
	}
	@Test
	public void parseExpressionWithParam() throws ParseException, TMLExpressionException {
//		Object o = CachedExpression.getInstance().evaluate("?[/@ClubId] AND Trim([/@ClubId]) != ''", input, null, null, null, null, null, null,null);
		Object o = ExpressionCache.getInstance().evaluate("?[/@Param]", input, null, null, null, null, null,null,null);
		Assert.assertEquals(true, o);
		Object o2 = ExpressionCache.getInstance().evaluate("?[/@Paramzz]", input, null, null, null, null, null,null,null);
		Assert.assertFalse((Boolean)o2);
		Object o3 = ExpressionCache.getInstance().evaluate("?[/@Param] AND [/@Param] != ''", input, null, null, null, null, null,null,null);
		Assert.assertTrue((Boolean)o3);
		System.err.println("ss: "+o3);
//        System.err.println("Result: "+ss.apply(input, null, null, null, null, null, null,null));
	}

	@Test
	public void parsePerformanceTest() throws TMLExpressionException, SystemException {
		long before = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Object o3 = Expression.evaluate("?[/@Param] AND [/@Param] != ''", input);
		}
		long now = System.currentTimeMillis();
		long compiledTime = (now-before);
		System.err.println("Compiled Parsing: "+compiledTime);
		Expression.dumpStats();
		before = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Object o3 = Expression.evaluate("?[/@Param] AND [/@Param] != ''", input);
		}
		now = System.currentTimeMillis();
		compiledTime = (now-before);
		System.err.println("Compiled parsing after warm-up: "+compiledTime);
		Expression.dumpStats();
		

		before = System.currentTimeMillis();
		Expression.compileExpressions = true;
		for (int i = 0; i < 100000; i++) {
			Object o3 = Expression.evaluate("?[/@Param] AND [/@Param] != ''", input);
		}
		now = System.currentTimeMillis();
		compiledTime = (now-before);
		System.err.println("Interpreted Parsing: "+compiledTime);
		
	}
	
}
