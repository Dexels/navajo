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

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.NavajoConfig;

public abstract class FunctionInterface {

    private ArrayList operandList = null;
    protected Navajo inMessage = null;
    protected Message currentMessage = null;

    public abstract String remarks();
    public abstract String usage();

    public final void reset() {
        operandList = new ArrayList();
    }
    public final void insertOperand(Object o) {
        operandList.add(o);
    }

    public abstract Object evaluate() throws TMLExpressionException;

    protected final ArrayList getOperands() {
        return operandList;
    }

    protected final Navajo getNavajo() {
      return this.inMessage;
    }

    protected final Message getCurrentMessage() {
      return this.currentMessage;
    }

    protected final Object getOperand(int index) throws TMLExpressionException {
        if (index >= operandList.size())
            throw new TMLExpressionException("Function Exception: Missing operand (index = " + index + ")");
        else
            return operandList.get(index);
    }

}
