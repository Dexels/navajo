package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class DecimalChar extends FunctionInterface {

    public DecimalChar() {// Hallo
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        String result = "";
        try{
        	int cr = Integer.parseInt(a+"");
        	result = Character.toString((char) cr);
        }catch(Exception e){
        	throw new TMLExpressionException("Invalid operand specified: " + a.toString());
        }
        return result;
    }

    public String usage() {
        return "DecimalChar(int)";
    }

    public String remarks() {
        return "This function returns a string representation of the desired decimal character";
    }
}
