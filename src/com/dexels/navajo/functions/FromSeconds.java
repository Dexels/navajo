package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FromSeconds extends FunctionInterface{

  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null)
          return null;

        if (o instanceof Integer){
          return new ClockTime("00:" +((Integer)o).intValue());
        }

      return new Integer(-1);

    }

    public String usage() {
        return "";
    }

    public String remarks() {
        return "";
    }



  public static void main(String[] args) {
    FromSeconds ts = new FromSeconds();
    ts.reset();
    ts.insertOperand(new Integer(22));
    try{
      Object o = ts.evaluate();
      System.err.println("Sec: " + ((ClockTime)o).dateValue());
    }catch(Exception e){
    }

  }

}
