package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;

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

      public String remarks() {
          return "Will return a euro sign.";
      }

      public String usage() {
          return "Utility to allow euro signs in expressions, until the expression language fully supports it.";
      }

      public final Object evaluate() throws TMLExpressionException {
        return "\u20ac";
      }
  }

