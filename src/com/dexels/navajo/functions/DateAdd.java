package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class DateAdd extends FunctionInterface {

  public DateAdd() {
  }

  public String remarks() { return ""; }

  public String usage() { return ""; }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException
  {

    java.util.Date datum = (java.util.Date) this.getOperands().get(0);
    Integer arg = (Integer) this.getOperands().get(1);
    String field = (String) this.getOperands().get(2);
    Calendar cal = Calendar.getInstance();
    cal.setTime(datum);
    if (field.equals("YEAR")) {
      cal.add(Calendar.YEAR, arg.intValue());
    } else if (field.equals("MONTH")) {
      cal.add(Calendar.MONTH, arg.intValue());
    } else if (field.equals("DAY")) {
      cal.add(Calendar.DAY_OF_MONTH, arg.intValue());
    } else if (field.equals("WEEK")) {
      cal.add(Calendar.WEEK_OF_YEAR, arg.intValue());
    }
    return cal.getTime();
  }
}