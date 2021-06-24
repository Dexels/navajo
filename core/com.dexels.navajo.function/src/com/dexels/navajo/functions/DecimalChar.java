/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

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

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
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

    @Override
	public String usage() {
        return "DecimalChar(int)";
    }

    @Override
	public String remarks() {
        return "This function returns a string representation of the desired decimal character";
    }
}
