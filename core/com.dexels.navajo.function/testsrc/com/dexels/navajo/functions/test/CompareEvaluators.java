/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions.test;

import org.junit.Test;

public class CompareEvaluators {

	public CompareEvaluators() {
	}

	@Test
	public void testEvaluator() throws Exception {
		/* 
		Locale.setDefault(Locale.GERMAN);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.getDef("ToDouble");
		FunctionInterface td = fi.getInstance(ToDouble.class.getClassLoader(), "ToDouble");
    	Money m = new Money(5.0);
    	
    	String expr = "-1 * ToMoney( ( ToDouble(1000) / ( 100 + ( ToDouble(ToPercentage(0.2)) * 100 ) ) ) * ( 100 * ToDouble(ToPercentage(0.3)) ) )";
    	NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
    	NavajoFactory.getInstance().setExpressionEvaluator(new CachedExpressionEvaluator());
    	Operand o2 = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(expr, null,null,null);
    	System.err.println("o: "+o.value+" o2: "+o2.value);
    	Assert.assertEquals(o.value, o2.value);
    	//    	ToDouble td = new ToDouble();
    	td.reset();
    	td.insertOperand(m);
    	System.err.println(td.evaluate()+"");
    	Class<? extends Object>[] s =td.getReturnType();
    	for (Class<? extends Object> class1 : s) {
			System.err.println("typw: "+class1);
		}
		*/
    }
}
