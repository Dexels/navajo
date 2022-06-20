/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


public final class EqualsIgnoreCase  extends FunctionInterface {

    @Override
	public String remarks() {
        return "";
    }

    @Override
	public String usage() {
        return "";
    }
    
    @Override
	public boolean isPure() {
    		return true;
    }


    @Override
	public final Object evaluate() throws TMLExpressionException {

        List<?> operands = this.getOperands();

        if (operands.size() != 2)
            throw new TMLExpressionException("Invalid number of arguments for EqualsIgnoreCase()");
        String a = (String) operands.get(0);
        String b = (String) operands.get(1);

        return (a.equalsIgnoreCase(b));
    }
}
