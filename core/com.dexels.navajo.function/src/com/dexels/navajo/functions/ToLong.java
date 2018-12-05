package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


public final class ToLong extends FunctionInterface {

    public ToLong() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null || "".equals(o))
          return null;

        if(o instanceof Long) {
      	  return o;
        }
        if(o instanceof java.util.Date) {
        	  return new Long(((java.util.Date)o).getTime());
          }
        if (o instanceof Double)
          return new Long( (int) ((Double) o).doubleValue() );


        return new Long(Long.parseLong(o + ""));

    }

    @Override
	public String usage() {
        return "ToLong(Object)";
    }

    @Override
	public String remarks() {
        return "Get an long representation of given object.";
    }
    @Override
	public boolean isPure() {
    		return false;
    }

    public static void main(String [] args) {
    }

}
