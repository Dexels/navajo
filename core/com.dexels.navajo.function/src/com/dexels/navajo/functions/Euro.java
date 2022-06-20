/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public final class Euro  extends FunctionInterface{
  public Euro() {
  }
  @Override
	public boolean isPure() {
  		return true;
  }

      @Override
	public String remarks() {
          return "Will return a euro sign.";
      }

      @Override
	public String usage() {
          return "Utility to allow euro signs in expressions, until the expression language fully supports it.";
      }

      @Override
	public final Object evaluate() throws TMLExpressionException {
        return "\u20ac";
      }
  }

