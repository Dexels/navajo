/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.regex.Pattern;

import com.dexels.navajo.expression.api.FunctionInterface;


public class QuotePattern extends FunctionInterface {

  public QuotePattern() {}

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
      String s = (String) this.getOperands().get(0);

      if (s == null)
        return null;

      return Pattern.quote(s);
     
  }

  @Override
public String usage() {
      return "QuotePattern(String)";
  }

  @Override
public String remarks() {
      return "Make a string regex-safe by using Pattern.quote";
  }

}
