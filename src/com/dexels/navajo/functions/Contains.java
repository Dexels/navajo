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
import com.dexels.navajo.document.Operand;


public final class Contains extends FunctionInterface {

    public String remarks() {
        return "Checks whether an arraylist contains a certain object";
    }

    public final boolean contains(ArrayList list, Object o) throws TMLExpressionException {

        boolean result = false;

        for (int i = 0; i < list.size(); i++) {
            Object a = list.get(i);

            if (a instanceof ArrayList)
                result = contains((ArrayList) a, o);
            else
                result = Utils.equals(a, o);
            if (result == true)
                i = list.size() + 1;
        }
        return result;
    }

    public final Object evaluate() throws TMLExpressionException {
        // input (ArrayList, Object).
        if (this.getOperands().size() != 2)
            throw new TMLExpressionException("Contains(ArrayList, Object) expected");
        Object a = this.getOperands().get(0);

        if (!(a instanceof ArrayList))
            throw new TMLExpressionException("Contains(ArrayList, Object) expected");
        Object b = this.getOperands().get(1);

        return new Boolean(contains((ArrayList) a, b));
    }

    public static void main(String [] args) throws Exception {
      Operand o = Expression.evaluate("{1,2,3,4}", null);
      System.err.println("o = " + o.value);
    }
}
