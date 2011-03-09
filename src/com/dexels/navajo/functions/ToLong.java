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


public final class ToLong extends FunctionInterface {

    public ToLong() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null || "".equals(o))
          return null;

        if(o instanceof Long) {
      	  return (Long)o;
        }
        if(o instanceof java.util.Date) {
        	  return new Long(((java.util.Date)o).getTime());
          }
        if (o instanceof Double)
          return new Long( (int) ((Double) o).doubleValue() );


        return new Long(Long.parseLong(o + ""));

    }

    public String usage() {
        return "ToLong(Object)";
    }

    public String remarks() {
        return "Get an long representation of given object.";
    }

    public static void main(String [] args) {
    }

}
