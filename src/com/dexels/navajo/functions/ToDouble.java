package com.dexels.navajo.functions;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import com.dexels.navajo.parser.*;

public class ToDouble extends FunctionInterface {

  public ToDouble()  {
  }

   public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    String s = (String) this.getOperands().get(0);
    return new Double(s);
  }

  public String usage() {
    return "";
  }

  public String remarks() {
    return "";
  }

}