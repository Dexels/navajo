package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class Date extends FunctionInterface {

  public Date() {
  }

  public String remarks() { return ""; }
  public String usage() { return ""; }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
      String arg = (String) this.getOperands().get(0);
      java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
      java.util.Date date = null;
      try {
        date = format.parse(arg);
      } catch (Exception e) {
        throw new TMLExpressionException("Invalid date format: " + arg);
      }
      return date;
  }
}