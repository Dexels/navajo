package com.dexels.navajo.functions.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.config.runtime.RuntimeConfig;
import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;
import com.dexels.navajo.parser.compiled.api.ParseMode;
import com.dexels.navajo.script.api.SystemException;

public class TestCompiledExpression {

	private Navajo input;
	
	
	private final static Logger logger = LoggerFactory.getLogger(TestCompiledExpression.class);



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
	public void parseFunction() throws TMLExpressionException {

		Object o = ExpressionCache.getInstance().evaluate("ToUpper('ble')", input,(Message)null, (Message)null, (Selection)null, null, null,null,Optional.<ImmutableMessage>empty(),Optional.<ImmutableMessage>empty());
		System.err.println(": "+o);
		Assert.assertEquals("BLE", o);
	}
	
	@Test
	public void testFunctionParamTypeError() throws TMLExpressionException {
		List<String> problems = new ArrayList<>();
		ContextExpression o = ExpressionCache.getInstance().parse(problems,"ToUpper(1)",ParseMode.DEFAULT);
		System.err.println("problems: "+problems);
		System.err.println("returntype: "+o.returnType());
		if(RuntimeConfig.STRICT_TYPECHECK.getValue()!=null) {
			Assert.assertEquals(1, problems.size());
		} else {
			Assert.assertEquals("Don't expect problems to appear when STRICT_TYPECHECK is false", 0,problems.size());
//			Assert.fail("Failed test, ony valid when STRICT_TYPECHECK. Set env for unit tests.");
		}
		
	}
	
	@Test
	public void testNestedFunctionType() throws TMLExpressionException {
		List<String> problems = new ArrayList<>();
		ContextExpression o = ExpressionCache.getInstance().parse(problems,"ToUpper(ToLower('Bob'))",ParseMode.DEFAULT);
		System.err.println("problems: "+problems);
		System.err.println("returntype: "+o.returnType().orElse("<unknown>"));
		Assert.assertTrue("Expected a return type here", o.returnType().isPresent());
		Assert.assertEquals("string", o.returnType().get());
	}
	
	@Test
	public void testFunctionType() {
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems,"ToUpper([whatever])",ParseMode.DEFAULT);
		Assert.assertEquals(Property.STRING_PROPERTY, cx.returnType().get());
	}
	

	@Test
	public void testForall() throws TMLExpressionException, SystemException {
		List<String> problems = new ArrayList<>();
		ExpressionCache ce = ExpressionCache.getInstance();
		String ext ="FORALL( '/TestArrayMessageMessage', `! ?[Delete] OR ! [Delete] OR [/__globals__/ApplicationInstance] != 'TENANT' OR ! StringFunction( 'matches', [ChargeCodeId], '.*[-]+7[0-9][A-z]*$' )` )";
//		String expression = "FORALL( '/Charges' , `! ?[Delete] OR ! [Delete]  OR [/__globals__/ApplicationInstance] != 'SOMETENANT' OR [SomeProperty] == 'SOMESTRING' `)";
//		ce.parse(problems, expression, mode, allowLiteralResolve)
		ContextExpression cx = ce.parse(problems,ext,ParseMode.DEFAULT,true);
		System.err.println("Problems: "+problems);
		Assert.assertEquals(0, problems.size());
		cx.apply(input,Optional.empty(),Optional.empty());
//		Operand result = Expression.evaluate(expression, testDoc,null,topMessage);
		
	}

}
