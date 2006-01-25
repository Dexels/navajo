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

public class ToSeconds extends FunctionInterface{

  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null)
          return null;

        if (o instanceof ClockTime){ // ClockTime does not support seconds correctly
          System.err.println("Sec: " + ( (ClockTime) o).calendarValue().getTime());
          return new Integer( ( (ClockTime) o).calendarValue().get(Calendar.MINUTE));
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
    ToSeconds ts = new ToSeconds();
    ts.reset();
    System.err.println("Time: " + Calendar.getInstance().getTime());
    ts.insertOperand(new ClockTime("00:22:45"));
    try{
      Object o = ts.evaluate();
      System.err.println("Sec: " + ((Integer)o).intValue());
    }catch(Exception e){
    }

  }

}
