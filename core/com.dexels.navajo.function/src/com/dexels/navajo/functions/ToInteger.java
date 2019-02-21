package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


public final class ToInteger extends FunctionInterface {

    public ToInteger() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null || "".equals(o))
          return null;

        if (o instanceof Double)
          return Integer.valueOf( (int) ((Double) o).doubleValue() );


        return Integer.valueOf(Integer.parseInt(o + ""));

    }

    @Override
	public String usage() {
        return "ToInteger(Object)";
    }

    @Override
	public String remarks() {
        return "Get an integer representation of given object.";
    }

    public static void main(String [] args) {
      System.err.println(new java.util.Date().getTime()+"");
    }

}
