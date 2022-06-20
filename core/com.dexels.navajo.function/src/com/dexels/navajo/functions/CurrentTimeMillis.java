/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;


public final class CurrentTimeMillis extends FunctionInterface {

  @Override
public String remarks() {
   return "CurrentTimeMillis(), returns the time in millis since 1/1/1970";
  }
  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    return System.currentTimeMillis()+"";
  }
  @Override
public String usage() {
   return "CurrentTimeMillis()";
  }


}
