/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dexels.navajo.expression.api.FunctionInterface;

public final class Now extends FunctionInterface {

  @Override
public String remarks() {
    return "Now() returns the current timestamp as a string in the following format: yyyy/MM/dd HH:uu:mm";
  }
  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    Date today = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return format.format(today);
  }
  @Override
public String usage() {
    return "Now()";
  }

  public static void main(String [] args) throws Exception {
    Now n = new Now();
    n.reset();
    String result = (String) n.evaluate();
    System.err.println("result = " + result);
  }
}