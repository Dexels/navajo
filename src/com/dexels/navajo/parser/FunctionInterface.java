package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.util.*;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.NavajoConfig;

public abstract class FunctionInterface {

    private ArrayList operandList = null;

    public abstract String remarks();
    public abstract String usage();

    public void reset() {
        operandList = new ArrayList();
    }
    public void insertOperand(Object o) {
        operandList.add(o);
    }

    public abstract Object evaluate() throws TMLExpressionException;

    protected ArrayList getOperands() {
        return operandList;
    }

    protected Object getOperand(int index) throws TMLExpressionException {
        if (index >= operandList.size())
            throw new TMLExpressionException("Function Exception: Missing operand (index = " + index + ")");
        else
            return operandList.get(index);
    }

}
