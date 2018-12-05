package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


public final class ToUpper extends FunctionInterface {

    public ToUpper() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object op = this.getOperands().get(0);
        

        if (op == null)
          return null;
        
        return op.toString().toUpperCase();

    }

    @Override
	public String usage() {
        return "ToUpper(String s)";
    }

    @Override
	public String remarks() {
        return "Get an uppercase representation of given string.";
    }
    @Override
	public boolean isPure() {
    		return true;
    }

}
