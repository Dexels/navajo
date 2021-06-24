/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class ParameterList extends FunctionInterface {

    public ParameterList() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Integer count = (Integer) this.getOperands().get(0);
        StringBuffer result = new StringBuffer(count.intValue() * 2);

        for (int i = 0; i < (count.intValue() - 1); i++) {
            result.append("?,");
        }
        result.append("?");
        return result.toString();
    }

    @Override
	public String usage() {
        return "ParameterList(count)";
    }

    @Override
	public String remarks() {
        return "Create a list of comma separate ? values for use in SQL queries";
    }
}
