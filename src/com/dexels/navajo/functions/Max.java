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


public class Max extends FunctionInterface {

    public String remarks() {
        return "";
    }

    public String usage() {
        return "";
    }

    public Object evaluate() throws TMLExpressionException {

        ArrayList operands = this.getOperands();

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

            if (!(a instanceof ArrayList))
                throw new TMLExpressionException("Invalid number of arguments for Max()");
            ArrayList list = (ArrayList) a;
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
