/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
   		return true;
  }

}
