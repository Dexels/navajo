package com.dexels.navajo.functions;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

import java.util.*;
import com.dexels.navajo.parser.*;


public final class Sum extends FunctionInterface {

    public String remarks() {
        return "Get the sum of values in a given list.";
    }

    public String usage() {
        return "Sum(ArrayList)";
    }

    public final Object sumList(ArrayList list) throws TMLExpressionException {

        Object sum = null;

        for (int i = 0; i < list.size(); i++) {
            Object b = list.get(i);

            if (b instanceof ArrayList) {
                sum = Utils.add(sum, sumList((ArrayList) b));
            } else {
                sum = Utils.add(sum, b);
            }
        }
        return sum;
    }

    public Object evaluate() throws TMLExpressionException {
        return sumList(this.getOperands());
    }
}
