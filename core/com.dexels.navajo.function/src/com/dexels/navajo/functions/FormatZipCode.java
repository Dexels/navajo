/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * <p>Title: <h3>SportLink Services</h3><br></p>
 * <p>Description: Web Services for the SportLink Project<br><br></p>
 * <p>Copyright: Copyright (c) 2002<br></p>
 * <p>Company: Dexels.com<br></p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public final class FormatZipCode extends FunctionInterface {

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    Object o = getOperand(0);
    // reverse?
    Boolean b = (getOperands().size() > 1) ? (Boolean) getOperand(1) : null;

    Pattern numericPattern = Pattern.compile("[0-9]{4}");
    Pattern alphaPattern = Pattern.compile("[A-z]{2}");
    if (o instanceof String) {
      String s = (String) o;
      try {
        Matcher numericMatch = numericPattern.matcher(s);
        Matcher alphaMatch = alphaPattern.matcher(s);
        numericMatch.find();
        alphaMatch.find();
        String numericString = numericMatch.group();
        String alphaString = alphaMatch.group();
        if (numericString == null || alphaString == null)
          return o;
        if (b == null)
          return numericString + " " + alphaString;
        else
          return numericString + alphaString;
      }
      catch (Exception ree) {
         return o;
      }
    } else {
      if (o == null)
        return null;
      else
        return o;
    }
  }
  @Override
	public boolean isPure() {
  		return true;
  }

  @Override
public String usage() {
    return "FormatZipCode(ZipCode)";
  }
  @Override
public String remarks() {
   return "Formats a zipcode: 1621AB -> 1621 AB";
  }

//  public static void main(String args[]) throws Exception {
//    FormatZipCode f = new FormatZipCode();
//    f.reset();
//    f.insertOperand("2900 BA");
//    f.insertOperand(new Boolean(false));
//    String r = (String) f.evaluate();
//    System.out.println("resultaat = " + r);
//  }
}