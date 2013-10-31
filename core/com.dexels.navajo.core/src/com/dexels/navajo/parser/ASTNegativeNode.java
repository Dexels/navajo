package com.dexels.navajo.parser;

import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;


public final class ASTNegativeNode extends SimpleNode {
    public ASTNegativeNode(int id) {
        super(id);
    }

    @Override
	public final Object interpret() throws TMLExpressionException {

        Object a = this.jjtGetChild(0).interpret();

        if (a instanceof String)
            return "-" + ((String) a);
        else if (a instanceof Integer)
            return new Integer(0 - ((Integer) a).intValue());
        else if (a instanceof Double)
            return new Double(0 - ((Double) a).doubleValue());
        else if (a instanceof Money)
          return new Money(0 - ((Money) a).doubleValue());
        else if (a instanceof Percentage)
          return new Percentage(0 - ((Percentage) a).doubleValue());
        else
          throw new TMLExpressionException("Illegal type encountered before negation");
    }

}
