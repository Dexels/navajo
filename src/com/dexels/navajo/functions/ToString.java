package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * $Id$
 *
 */

public class ToString extends FunctionInterface {

   public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object s = this.getOperands().get(0);
        if (s != null)
          return s.toString();
        else
          return null;
    }

    public String usage() {
        return "";
    }

    public String remarks() {
        return "";
    }
}