/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


public class ToPattern extends FunctionInterface {

  public ToPattern() {}

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
      String s = (String) this.getOperands().get(0);
      
      if (s == null)
          return null;

      int options = 0;
      if (this.getOperands().size() > 1) {
          String soptions = (String) this.getOperands().get(1);
          if (soptions.indexOf('i') > -1) {
              options = options | Pattern.CASE_INSENSITIVE;
          }
      }
      try {
          return Pattern.compile(s, options);
      } catch (PatternSyntaxException e) { 
          throw new TMLExpressionException("Invalid regex!");
      }
  }

  @Override
public String usage() {
      return "ToPattern(String pattern, string options)";
  }

  @Override
public String remarks() {
      return "Returns a regex pattern of the given string. Optionally use RegexQuote to quote a (part of) the regex ";
  }

}
