package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public class ElfProef extends FunctionInterface {

  public ElfProef() {
  }

  public String remarks() { return ""; }
  public String usage() { return ""; }


  public boolean elfProef(String nummer) {

    boolean result = false;

    if (nummer.length() != 9)
      result= false;

    int total = 0;
    for (int i = 0; i < (nummer.length());i++) {
      int digit = Integer.parseInt(nummer.charAt(i)+"");
      digit = (9-i) * digit;
      total += digit;
    }

    if (total % 11 == 0)
      result = true;
    else
      result = false;
    return result;
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object o = this.getOperands().get(0);
    if (o instanceof String)
      return new Boolean(elfProef((String) o));
    else if (o instanceof Integer)
      return new Boolean(elfProef(((Integer) o).intValue() + ""));
    else {
      throw new TMLExpressionException("Illegal argument type for function ElfProef(): " + o.getClass().getName());
    }
  }
}