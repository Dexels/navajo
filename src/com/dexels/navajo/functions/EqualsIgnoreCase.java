package com.dexels.navajo.functions;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

import java.util.*;
import com.dexels.navajo.parser.*;

public class EqualsIgnoreCase  extends FunctionInterface {

 public String remarks() { return ""; }
  public String usage() { return ""; }

  public Object evaluate() throws TMLExpressionException {

    ArrayList operands = this.getOperands();
    if (operands.size() != 2)
      throw new TMLExpressionException("Invalid number of arguments for EqualsIgnoreCase()");
    String a = (String) operands.get(0);
    String b = (String) operands.get(1);
    return new Boolean(a.equalsIgnoreCase(b));
  }
}