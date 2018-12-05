package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;


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

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
      String s = (String) this.getOperands().get(0);

      if (s == null)
        return null;

      return s.toLowerCase();
  }

  @Override
public String usage() {
      return "ToLower(String)";
  }

  @Override
public String remarks() {
      return "Returns a lowercase version of the supplied string.";
  }
  @Override
	public boolean isPure() {
  		return false;
  }

}
