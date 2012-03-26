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

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.Utils;


public final class Max extends FunctionInterface {

    public String remarks() {
        return "Return the maximum of to given numbers.";
    }

    public String usage() {
        return "Max(number, number), where number can be of type integer or double.";
    }

    @SuppressWarnings("rawtypes")
	public final Object evaluate() throws TMLExpressionException {

        List<?> operands = this.getOperands();

        if (operands.size() == 2) {
            Object a = operands.get(0);
            Object b = operands.get(1);
            double value1 = Utils.getDoubleValue(a);
            double value2 = Utils.getDoubleValue(b);
            double max = (value1 > value2) ? value1 : value2;

            if (a instanceof Double || b instanceof Double)
                return new Double(max);
            else
                return new Integer((int) max);
        } else if (operands.size() == 1) {
            // List as argument.
            Object a = operands.get(0);

            if (!(a instanceof List))
                throw new TMLExpressionException("Invalid number of arguments for Max()");
            List list = (List) a;
            double max = 0.0;
            boolean dvalue = false;

            for (int i = 0; i < list.size(); i++) {
                Object b = list.get(i);

                if (b instanceof Double)
                    dvalue = true;
                double val = Utils.getDoubleValue(b);

                max = (val > max) ? val : max;
            }
            if (dvalue)
                return new Double(max);
            else
                return new Integer((int) max);
        } else
            throw new TMLExpressionException("Invalid number of arguments for Max()");
    }
}
