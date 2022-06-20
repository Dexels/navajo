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

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Utils;


public final class Sum extends FunctionInterface {

    @Override
	public String remarks() {
        return "Get the sum of values in a given list.";
    }

    @Override
	public String usage() {
        return "Sum(ArrayList)";
    }

    @SuppressWarnings("rawtypes")
	public final Object sumList(List<?> list) throws TMLExpressionException {

        Object sum = null;

        for (int i = 0; i < list.size(); i++) {
            Object b = list.get(i);

            if (b instanceof ArrayList) {
                sum = Utils.add(sum, sumList((List) b),"<unknown>");
            } else {
                sum = Utils.add(sum, b,"<unknown>");
            }
        }
        return sum;
    }

    @Override
	public Object evaluate() throws TMLExpressionException {
        return sumList(this.getOperands());
    }
}
