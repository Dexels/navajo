package com.dexels.navajo.expression.compiled;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.NamedExpression;
import com.dexels.navajo.parser.compiled.ASTKeyValueNode;
import com.dexels.navajo.parser.compiled.ASTTransformerNode;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.SimpleNode;
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
		List<String> problems = new ArrayList<>();
        String expression = "1+1";
		ContextExpression ss =  ExpressionCache.getInstance().parse(problems,expression,fn->FunctionClassification.DEFAULT);
        ContextExpression ss2 =  ExpressionCache.getInstance().parse(problems,expression,fn->FunctionClassification.DEFAULT);
        if(!problems.isEmpty()) {
        		throw new TMLExpressionException(problems,expression);
        }
        System.err.println("ss: "+ss.isLiteral());
        System.err.println("ss2: "+ss2.isLiteral());
        System.err.println("Result: "+ss.apply(null, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value);
        Assert.assertEquals(2, ss.apply(null, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value);
        Assert.assertEquals(2, ss2.apply(null, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value);
        Assert.assertTrue(ss.isLiteral());
        Assert.assertTrue(ss2.isLiteral());

	}

	@Test
	public void testParseTml() throws ParseException, TMLExpressionException {
		String expression = "[/TestMessage/TestProperty]";
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		cp.Expression();
		List<String> problems = new ArrayList<>();
        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda(problems,sr.toString(),fn->FunctionClassification.DEFAULT);
        if(!problems.isEmpty()) {
    			throw new TMLExpressionException(problems,expression);
        }
        
        System.err.println("tml: "+ss.isLiteral());
        System.err.println("TMLVALUE: "+ss.apply(input, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value);
        Assert.assertEquals("TestValue", ss.apply(input, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value);
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
	public void testMultilineStringLiteral() throws ParseException, TMLExpressionException, SystemException {
		String clause = "'what is a haiku\n" + 
				"nothing but words, poetic?\n" + 
				"this is a haiku'";
//		StringReader sr = new StringReader(clause);
//		CompiledParser cp = new CompiledParser(sr);
		String o = (String) Expression.evaluate(clause,input, null, null, null).value;
		int lines = o.split("\n").length;
		Assert.assertEquals(3, lines);
	}
	
	@Test
	public void parseExpressionLiteral() throws ParseException, TMLExpressionException {
		Operand o = ExpressionCache.getInstance().evaluate("FORALL( '/TestArrayMessageMessage', `?[Property]`)", input, null, null, null, null, null,null,Optional.empty(),Optional.empty());
        System.err.println("ss: "+o);
//        System.err.println("Result: "+ss.apply(input, null, null, null, null, null, null,null));
	}
	@Test
	public void parseExpressionWithParam() throws ParseException, TMLExpressionException {
//		Object o = CachedExpression.getInstance().evaluate("?[/@ClubId] AND Trim([/@ClubId]) != ''", input, null, null, null, null, null, null,null);
		Object o = ExpressionCache.getInstance().evaluate("?[/@Param]", input, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value;
		Assert.assertEquals(true, o);
		Object o2 = ExpressionCache.getInstance().evaluate("?[/@Paramzz]", input, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value;
		Assert.assertFalse((Boolean)o2);
		Object o3 = ExpressionCache.getInstance().evaluate("?[/@Param] AND [/@Param] != ''", input, null, null, null, null, null,null,Optional.empty(),Optional.empty()).value;
		Assert.assertTrue((Boolean)o3);
		System.err.println("ss: "+o3);
//        System.err.println("Result: "+ss.apply(input, null, null, null, null, null, null,null));
	}

	@SuppressWarnings("unused")
	@Test @Ignore

	public void parsePerformanceTest() throws TMLExpressionException, SystemException {
		long before = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			Object o3 = Expression.evaluate("?[/@Param] AND [/@Param] != ''", input);
		}
		long now = System.currentTimeMillis();
		long compiledTime = (now-before);
		System.err.println("Compiled Parsing: "+compiledTime);
//		Expression.dumpStats();
		ExpressionCache.getInstance().printStats();
		before = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			Object o3 = Expression.evaluate("?[/@Param] AND [/@Param] != ''", input);
		}
		now = System.currentTimeMillis();
		compiledTime = (now-before);
		System.err.println("Compiled parsing after warm-up: "+compiledTime);
		ExpressionCache.getInstance().printStats();
		

		before = System.currentTimeMillis();
		Expression.compileExpressions = true;
		for (int i = 0; i < 100000; i++) {
			Object o3 = Expression.evaluate("?[/@Param] AND [/@Param] != ''", input);
		}
		now = System.currentTimeMillis();
		compiledTime = (now-before);
		System.err.println("Interpreted Parsing: "+compiledTime);
		
	}
	
	@Test
	public void testNamedExpression() throws ParseException {
		String expression = "aap=1+1";
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		cp.KeyValue();		
		ASTKeyValueNode atn = (ASTKeyValueNode) cp.getJJTree().rootNode();
		List<String> problems = new ArrayList<>();
		NamedExpression ne = (NamedExpression) atn.interpretToLambda(problems, expression,fn->FunctionClassification.DEFAULT);
		System.err.println("Problems: "+problems);
		Assert.assertEquals(0, problems.size());
		Assert.assertEquals("aap",ne.name);
		Assert.assertEquals(2, ne.apply().value);
	}

	@Test
	public void testFunctionCallWithNamedParams() throws ParseException {
        FunctionInterface testFunction = new AddTestFunction();
        FunctionDefinition fd = new FunctionDefinition(testFunction.getClass().getName(), "blib", "bleb", "blab");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("addtest",fd);
		String expression = "addtest(aap='blub',3+5,4)";
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		cp.Expression();
		SimpleNode atn = (SimpleNode) cp.getJJTree().rootNode();
		List<String> problems = new ArrayList<>();
		ContextExpression ne = atn.interpretToLambda(problems, expression,fn->FunctionClassification.DEFAULT);
		Operand result = ne.apply();
		System.err.println("Final: "+result.value);
		Assert.assertEquals("monkey", result.value);
	}

	@Test
	public void testEmptyFunctionCall() throws ParseException {
        FunctionInterface testFunction = new AddTestFunction();
        FunctionDefinition fd = new FunctionDefinition(testFunction.getClass().getName(), "blib", "bleb", "blab");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("addtest",fd);

		String expression = "addtest()";
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		cp.Expression();
		List<String> problems = new ArrayList<>();
        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda(problems,sr.toString(),fn->FunctionClassification.DEFAULT);
        Operand o = ss.apply();
		Assert.assertEquals("monkey", o.value);
        System.err.println(">> "+o);
	}
	
	@Test
	public void testMultiArgFunction() throws Exception {
        FunctionInterface testFunction = new AddTestFunction();
        FunctionDefinition fd = new FunctionDefinition(testFunction.getClass().getName(), "blib", "bleb", "blab");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("SingleValueQuery",fd);
		String expression = 	"SingleValueQuery( 'aap','noot' )";
		
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		List<String> problems = new ArrayList<>();
		cp.Expression();
        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda(problems,sr.toString(),fn->FunctionClassification.DEFAULT);
        System.err.println("ss: "+ss.getClass());
	}


	@Test
	public void testNestedNamedFunction() throws Exception {
        FunctionInterface testFunction = new AddTestFunction();
        FunctionDefinition fd = new FunctionDefinition(testFunction.getClass().getName(), "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("MysteryFunction",fd);
        
		String expression = 	"MysteryFunction(eep=MysteryFunction('blib','blob'), 'aap','noot' )";
		
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		List<String> problems = new ArrayList<>();
		cp.Expression();
        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda(problems,sr.toString(),fn->FunctionClassification.DEFAULT);
        System.err.println("ss: "+ss.apply().getClass());
	}
	
	@Test
	public void testNestedNamedParams() throws Exception {
        FunctionInterface testFunction = new ParameterNamesFunction();
        FunctionDefinition fd = new FunctionDefinition(testFunction.getClass().getName(), "description", "input", "result");
        FunctionFactoryFactory.getInstance().addExplicitFunctionDefinition("ParameterNamesFunction",fd);
        
		String expression = 	"ParameterNamesFunction(aap=1+1,noot=2+2)";
		
		StringReader sr = new StringReader(expression);
		CompiledParser cp = new CompiledParser(sr);
		List<String> problems = new ArrayList<>();
		cp.Expression();
        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda(problems,sr.toString(),fn->FunctionClassification.DEFAULT);
        System.err.println("ss: "+ss.apply().value);
        Assert.assertEquals("aap,noot", ss.apply().value);
	}
	
	@Test
	public void testUnicodeExpression() throws Exception {
		Operand result = Expression.evaluate("'\u20AC2,29'", null,null,null);
		System.err.println("Result:"+result.value);
	}	
	@Test
	public void testUnicodeExpressionEscaped() throws Exception {
		Operand result = Expression.evaluate("'\u20AC2,29'", null,null,null);
		System.err.println("Result:"+result.value);
	}	
	
	
//	Unicode(hex-string)
}
