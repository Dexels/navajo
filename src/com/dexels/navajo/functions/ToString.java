package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.Binary;
import java.io.StringWriter;


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

public final class ToString extends FunctionInterface {

   public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object s = this.getOperands().get(0);

        System.err.println("Type of s = " + s);
        if (s instanceof com.dexels.navajo.document.types.Binary) {
          Binary b = (Binary) s;
          byte [] data = b.getData();
          StringWriter w = new StringWriter();
          for (int i = 0; i < data.length; i++) {
            w.write(data[i]);
          }
          return w.toString();
        }

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