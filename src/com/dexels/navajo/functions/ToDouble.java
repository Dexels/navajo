package com.dexels.navajo.functions;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.parser.*;


public final class ToDouble extends FunctionInterface {

    public ToDouble() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperands().get(0);
        if (o==null) {
          return new Double(0);
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
    	Money m = new Money(5.0);
    	ToDouble td = new ToDouble();
    	td.reset();
    	td.insertOperand(m);
    	System.err.println(td.evaluate()+"");
    }

}
