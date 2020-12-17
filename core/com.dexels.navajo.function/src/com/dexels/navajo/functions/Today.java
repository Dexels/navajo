/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Calendar;
import java.util.Date;

import com.dexels.navajo.expression.api.FunctionInterface;

public final class Today extends FunctionInterface {

  @Override
public String remarks() {
    return "Today() returns the current date, with time at 00:00";
  }
  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    return c.getTime();
  }
  @Override
public String usage() {
    return "Today()";
  }

  public static void main(String [] args) throws Exception {
    Today n = new Today();
    n.reset();
    Date result = (Date) n.evaluate();
    System.err.println("result = " + result);
  }
}