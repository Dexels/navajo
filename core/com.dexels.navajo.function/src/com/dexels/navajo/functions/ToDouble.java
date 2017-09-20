package com.dexels.navajo.functions;


import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.parser.FunctionInterface;


public final class ToDouble extends FunctionInterface {

    public ToDouble() {}

    @Override
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

    @Override
	public String usage() {
        return "ToDouble(Object)";
    }

    @Override
	public String remarks() {
        return "Get a Double version of supplied object. Returns 0.0 if object is null.";
    }
    


}
