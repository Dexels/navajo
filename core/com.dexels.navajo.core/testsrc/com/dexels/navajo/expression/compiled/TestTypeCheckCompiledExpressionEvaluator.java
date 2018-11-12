package com.dexels.navajo.expression.compiled;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;
import com.dexels.navajo.parser.compiled.api.ParseMode;

public class TestTypeCheckCompiledExpressionEvaluator {

	@Test
	public void testBasicTypes() {
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems,"1",ParseMode.DEFAULT);
		Assert.assertEquals(Property.INTEGER_PROPERTY, cx.returnType().get());
	}

	@Test
	public void testEquals() {
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems, "1==1",ParseMode.DEFAULT);
		Assert.assertEquals(Property.BOOLEAN_PROPERTY, cx.returnType().get());
	}

	@Test
	public void testUnknown() {
		// test unknowable type
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems, "[bla]",ParseMode.DEFAULT);
		Assert.assertFalse(cx.returnType().isPresent());
	}

	@Test 
	public void testBadType() {
		// test unknowable type
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems,"1 AND 'monkey'",ParseMode.DEFAULT,false);
		System.err.println("Problems: "+problems);
		Assert.assertEquals(2,problems.size());
		Assert.assertEquals(Property.BOOLEAN_PROPERTY, cx.returnType().get());
	}
}
