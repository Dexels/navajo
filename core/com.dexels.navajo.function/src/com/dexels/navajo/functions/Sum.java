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

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
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
                sum = Utils.add(sum, sumList((List) b));
            } else {
                sum = Utils.add(sum, b);
            }
        }
        return sum;
    }

    @Override
	public Object evaluate() throws TMLExpressionException {
        return sumList(this.getOperands());
    }
}
