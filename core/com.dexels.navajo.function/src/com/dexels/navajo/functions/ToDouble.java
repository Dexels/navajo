package com.dexels.navajo.functions;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.util.Locale;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.parser.FunctionInterface;


public final class ToDouble extends FunctionInterface {

    public ToDouble() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		Object o = this.getOperands().get(0);
        if (o==null) {
          return new Double(0);
        }
        if(o instanceof Money) {
      	  return ((Money)o).doubleValue();
        }
        return new Double(o+"");
    }

    public String usage() {
        return "ToDouble(Object)";
    }

    public String remarks() {
        return "Get a Double version of supplied object. Returns 0.0 if object is null.";
    }
    
    public static void main(String [] args) throws Exception {
   	 Locale.setDefault(Locale.GERMAN);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.getDef("ToDouble");
		FunctionInterface td = fi.getInstance(ToDouble.class.getClassLoader(), "ToDouble");
    	Money m = new Money(5.0);
    	
    	String expr = "-1 * ToMoney( ( ToDouble(1000) / ( 100 + ( ToDouble(ToPercentage(0.2)) * 100 ) ) ) * ( 100 * ToDouble(ToPercentage(0.3)) ) )";
    	NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
    	Operand o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(expr, null);
    	System.err.println("o: "+o.value);
    	//    	ToDouble td = new ToDouble();
    	td.reset();
    	td.insertOperand(m);
    	System.err.println(td.evaluate()+"");
    	Class[] s =td.getReturnType();
    	for (Class class1 : s) {
			System.err.println("typw: "+class1);
		}
    }

}
