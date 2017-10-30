package com.dexels.navajo.functions.test;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.functions.ToDouble;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;

public class CompareEvaluators {

	public CompareEvaluators() {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEvaluator() throws Exception {
   	 Locale.setDefault(Locale.GERMAN);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.getDef("ToDouble");
		FunctionInterface td = fi.getInstance(ToDouble.class.getClassLoader(), "ToDouble");
    	Money m = new Money(5.0);
    	
    	String expr = "-1 * ToMoney( ( ToDouble(1000) / ( 100 + ( ToDouble(ToPercentage(0.2)) * 100 ) ) ) * ( 100 * ToDouble(ToPercentage(0.3)) ) )";
    	NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
    	Operand o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(expr, null,null,null);
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
    }
}
