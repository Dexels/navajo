package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;
import java.util.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public class CheckRange extends FunctionInterface {

    public void prototype(ArrayList list, int a) {}

    public CheckRange() {}

    public String remarks() {
        return "";
    }

    public String usage() {
        return "";
    }

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        ArrayList list = (ArrayList) this.getOperands().get(0);
        Integer a = (Integer) this.getOperands().get(1);

        for (int i = 0; i < list.size(); i++) {
            Object operand = list.get(i);

            if (operand instanceof Integer) {
                Integer x = (Integer) operand;

                if (!((x.intValue() >= a.intValue()) || (x.intValue() == 0)))
                    return new Boolean(false);
            } else if (operand instanceof Double) {
                Double x = (Double) operand;

                if (!((x.doubleValue() >= a.intValue())
                        || (x.doubleValue() == 0)))
                    return new Boolean(false);
            } else
                throw new TMLExpressionException("Invalid operand for CheckRange function: " + operand.getClass().getName());
        }
        return new Boolean(true);
    }
}
