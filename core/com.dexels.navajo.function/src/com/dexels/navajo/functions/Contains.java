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


public final class Contains extends FunctionInterface {

    @Override
	public String remarks() {
        return "Checks whether an arraylist contains a certain object";
    }
    @Override
	public boolean isPure() {
    		return true;
    }

    public final boolean contains(List<?> list, Object o) throws TMLExpressionException {

        boolean result = false;

        for (int i = 0; i < list.size(); i++) {
            Object a = list.get(i);

            if (a instanceof List)
                result = contains((List<?>) a, o);
            else
                result = Utils.equals(a, o,"<unknown>");
            if (result)
                i = list.size() + 1;
        }
        return result;
    }

    @Override
	public final Object evaluate() throws TMLExpressionException {
        // input (ArrayList, Object).
        if (this.getOperands().size() != 2)
            throw new TMLExpressionException("Contains(ArrayList, Object) expected");
        Object a = this.getOperands().get(0);

        if (!(a instanceof List))
            throw new TMLExpressionException("Contains(ArrayList, Object) expected");
        Object b = this.getOperands().get(1);

        return (contains((List<?>) a, b));
    }

}
