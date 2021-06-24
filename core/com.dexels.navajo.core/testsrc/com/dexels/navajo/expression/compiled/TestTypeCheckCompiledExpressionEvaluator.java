/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.expression.compiled;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;

public class TestTypeCheckCompiledExpressionEvaluator {

	@Test
	public void testBasicTypes() {
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems,"1",fn->FunctionClassification.DEFAULT);
		Assert.assertEquals(Property.INTEGER_PROPERTY, cx.returnType().get());
	}

	@Test
	public void testEquals() {
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems, "1==1",fn->FunctionClassification.DEFAULT);
		Assert.assertEquals(Property.BOOLEAN_PROPERTY, cx.returnType().get());
	}

	@Test
	public void testUnknown() {
		// test unknowable type
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		ContextExpression cx = ce.parse(problems, "[bla]",fn->FunctionClassification.DEFAULT);
		Assert.assertFalse(cx.returnType().isPresent());
	}

	@Test 
	public void testBadType() {
		// test unknowable type
		ExpressionCache ce = ExpressionCache.getInstance();
		List<String> problems = new ArrayList<>();
		// the [] expression has been added to prevent the expression to be pre-evaluated (as all other params are literals
		ContextExpression cx = ce.parse(problems,"1 AND 'monkey' AND [whoop]",fn->FunctionClassification.DEFAULT);
		System.err.println("Problems: "+problems);
		Assert.assertEquals(2,problems.size());
		Assert.assertEquals(Property.BOOLEAN_PROPERTY, cx.returnType().get());
	}
}
