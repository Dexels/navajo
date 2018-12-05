package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class ParameterList extends FunctionInterface {

    public ParameterList() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Integer count = (Integer) this.getOperands().get(0);
        StringBuffer result = new StringBuffer(count.intValue() * 2);

        for (int i = 0; i < (count.intValue() - 1); i++) {
            result.append("?,");
        }
        result.append("?");
        return result.toString();
    }

    @Override
	public String usage() {
        return "ParameterList(count)";
    }

    @Override
	public String remarks() {
        return "Create a list of comma separate ? values for use in SQL queries";
    }
}
