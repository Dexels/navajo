package com.dexels.navajo.functions;


import java.util.List;

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

public final class CheckRange extends FunctionInterface {

//    public void prototype(List<?> list, int a) {}

    public CheckRange() {}

    @Override
	public String remarks() {
        return "Check if a list of values is zero or less than a given number.";
    }

    @Override
	public boolean isPure() {
    		return true;
    }

    @Override
	public String usage() {
        return "CheckRange(List, Integer) where List is an ArrayList containing integers or doubles.";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        List<?> list = (List<?>) this.getOperands().get(0);
        Integer a = (Integer) this.getOperands().get(1);

        for (int i = 0; i < list.size(); i++) {
            Object operand = list.get(i);

            if (operand instanceof Integer) {
                Integer x = (Integer) operand;

                if (!((x.intValue() >= a.intValue()) || (x.intValue() == 0)))
                    return Boolean.FALSE;
            } else if (operand instanceof Double) {
                Double x = (Double) operand;

                if (!((x.doubleValue() >= a.intValue())
                        || (x.doubleValue() == 0)))
                    return Boolean.FALSE;
            } else
                throw new TMLExpressionException("Invalid operand for CheckRange function: " + operand.getClass().getName());
        }
        return Boolean.TRUE;
    }
}
