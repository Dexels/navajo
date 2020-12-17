/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Date;

import com.dexels.navajo.expression.api.FunctionInterface;

public final class Sysdate extends FunctionInterface {

  @Override
public String remarks() {
    return "Sysdate() returns the current date, with time";
  }
  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {	 
    return new Date();
  }
  @Override
public String usage() {
    return "Sysdate()";
  }

  public static void main(String [] args) throws Exception {
    Sysdate n = new Sysdate();
    n.reset();
    Date result = (Date) n.evaluate();
    System.err.println("result = " + result);
  }
}