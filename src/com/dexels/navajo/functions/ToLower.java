package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class ToLower extends FunctionInterface {

  public ToLower() {}

  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
      String s = (String) this.getOperands().get(0);

      if (s == null)
        return null;

      return s.toLowerCase();
  }

  public String usage() {
      return "ToLower(String)";
  }

  public String remarks() {
      return "Returns a lowercase version of the supplied string.";
  }

}
