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

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    java.util.Date datum = (java.util.Date) this.getOperands().get(0);
    Integer arg = (Integer) this.getOperands().get(1);
    String field = (String) this.getOperands().get(2);
    Calendar cal = Calendar.getInstance();
    cal.setTime(datum);
    if (field.equals("YEAR")) {
      cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + arg.intValue());
    } else if (field.equals("MONTH")) {
      cal.set(Calendar.MONTH, cal.get(Calendar.MONTH + arg.intValue()));
    }
    return cal.getTime();
  }
}