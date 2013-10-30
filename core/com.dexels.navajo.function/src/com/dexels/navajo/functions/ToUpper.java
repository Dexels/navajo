package com.dexels.navajo.functions;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import com.dexels.navajo.parser.FunctionInterface;


public final class ToUpper extends FunctionInterface {

    public ToUpper() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        String s = (String) this.getOperands().get(0);

        if (s == null)
          return null;

        return s.toUpperCase();
    }

    @Override
	public String usage() {
        return "ToUpper(String s)";
    }

    @Override
	public String remarks() {
        return "Get an uppercase representation of given string.";
    }
}
