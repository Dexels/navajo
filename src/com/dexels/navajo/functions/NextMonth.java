package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public class NextMonth extends FunctionInterface {

  public NextMonth() {
  }

  public String remarks() { return ""; }
  public String usage() { return ""; }


  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    java.util.Date datum = new java.util.Date();
    Calendar c = Calendar.getInstance();
    c.setTime(datum);

    Integer arg = (Integer) this.getOperands().get(0);
    int offset = arg.intValue();
    c.set(c.get(Calendar.YEAR) + offset, c.get(Calendar.MONTH) + 1, 1);

    java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyy-MM-dd");

    //return formatter.format(c.getTime());
    return c.getTime();
  }
}