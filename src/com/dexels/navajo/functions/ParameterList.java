package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ParameterList extends FunctionInterface {

  public ParameterList() {
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Integer count = (Integer) this.getOperands().get(0);
    StringBuffer result = new StringBuffer(count.intValue() * 2);
    for (int i = 0; i < (count.intValue()-1); i++) {
      result.append("?,");
    }
    result.append("?");
    return result.toString();
  }

  public String usage() {
    return "ParameterList(count)";
  }
  public String remarks() {
    return "Create a list of comma separate ? values for use in SQL queries";
  }
}