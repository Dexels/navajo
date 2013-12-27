package com.dexels.navajo.functions;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Matthijs Philip
 * @version $Id$
 */

import com.dexels.navajo.parser.FunctionInterface;


public final class ToInteger extends FunctionInterface {

    public ToInteger() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null || "".equals(o))
          return null;

        if (o instanceof Double)
          return new Integer( (int) ((Double) o).doubleValue() );


        return new Integer(Integer.parseInt(o + ""));

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
