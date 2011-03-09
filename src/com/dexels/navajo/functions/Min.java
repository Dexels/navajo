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

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.Utils;


public final class Min extends FunctionInterface {

	public String remarks() {
    return "Return the minimum of to given numbers.";
}

public String usage() {
    return "Min(number, number), where number can be of type integer or double.";
}

    public final Object evaluate() throws TMLExpressionException {

        ArrayList operands = this.getOperands();

        if (operands.size() == 2) {
            Object a = operands.get(0);
            Object b = operands.get(1);
            double value1 = Utils.getDoubleValue(a);
            double value2 = Utils.getDoubleValue(b);
            double min = (value1 < value2) ? value1 : value2;

            if (a instanceof Double || b instanceof Double)
                return new Double(min);
            else
                return new Integer((int) min);
        } else if (operands.size() == 1) {
            // List as argument.
            Object a = operands.get(0);

            if (!(a instanceof ArrayList))
                throw new TMLExpressionException("Invalid number of arguments for Min()");
            ArrayList list = (ArrayList) a;
            double min = java.lang.Integer.MAX_VALUE;
            boolean dvalue = false;

            for (int i = 0; i < list.size(); i++) {
                Object b = list.get(i);

                if (b instanceof Double)
                    dvalue = true;
                double val = Utils.getDoubleValue(b);

                min = (val < min) ? val : min;
            }
            if (dvalue)
                return new Double(min);
            else
                return new Integer((int) min);
        } else
            throw new TMLExpressionException("Invalid number of arguments for Min()");
    }
}
