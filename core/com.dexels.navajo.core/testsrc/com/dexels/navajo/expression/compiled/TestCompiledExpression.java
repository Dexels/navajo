package com.dexels.navajo.expression.compiled;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ContextExpression;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.CachedExpression;

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
        ContextExpression ss =  CachedExpression.getInstance().parse("1+1");
        ContextExpression ss2 =  CachedExpression.getInstance().parse("1+1");
        System.err.println("ss: "+ss.isLiteral());
        System.err.println("ss2: "+ss2.isLiteral());
        System.err.println("Result: "+ss.apply(null, null, null, null, null, null,null));
        Assert.assertEquals(2, ss.apply(null, null, null, null, null, null,null));
        Assert.assertEquals(2, ss2.apply(null, null, null, null, null, null,null));
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
        System.err.println("TMLVALUE: "+ss.apply(input, null, null, null, null, null,null));
        Assert.assertEquals("TestValue", ss.apply(input, null, null, null, null, null,null));
        Assert.assertFalse(ss.isLiteral());
	}

	@Test
	public void parseExpressionLiteral() throws ParseException, TMLExpressionException {
		Object o = CachedExpression.getInstance().evaluate("FORALL( '/TestArrayMessageMessage', `?[Property]`)", input, null, null, null, null, null,null);
        System.err.println("ss: "+o);
//        System.err.println("Result: "+ss.apply(input, null, null, null, null, null, null,null));
	}
	@Test
	public void parseExpressionWithParam() throws ParseException, TMLExpressionException {
//		Object o = CachedExpression.getInstance().evaluate("?[/@ClubId] AND Trim([/@ClubId]) != ''", input, null, null, null, null, null, null,null);
		Object o = CachedExpression.getInstance().evaluate("?[/@Param]", input, null, null, null, null, null,null);
		Assert.assertEquals(true, o);
		Object o2 = CachedExpression.getInstance().evaluate("?[/@Paramzz]", input, null, null, null, null, null,null);
		Assert.assertFalse((Boolean)o2);
		Object o3 = CachedExpression.getInstance().evaluate("?[/@Param] AND [/@Param] != ''", input, null, null, null, null, null,null);
		Assert.assertTrue((Boolean)o3);
		System.err.println("ss: "+o3);
//        System.err.println("Result: "+ss.apply(input, null, null, null, null, null, null,null));
	}

	
}
