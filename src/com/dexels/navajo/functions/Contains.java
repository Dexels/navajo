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


public class Contains extends FunctionInterface {

    public String usage() {
        return "Contains(list, string)\nContains(list, float)\nContains(list, integer)";
    }

    public String remarks() {
        return "";
    }

    public boolean contains(ArrayList list, Object o) throws TMLExpressionException {

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

    public Object evaluate() throws TMLExpressionException {
        // input (ArrayList, Object).
        if (this.getOperands().size() != 2)
            throw new TMLExpressionException("Contains(ArrayList, Object) expected");
        Object a = this.getOperands().get(0);

        if (!(a instanceof ArrayList))
            throw new TMLExpressionException("Contains(ArrayList, Object) expected");
        Object b = this.getOperands().get(1);

        return new Boolean(contains((ArrayList) a, b));
    }
}
