package com.dexels.navajo.parser;

import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;


public final class ASTMulNode extends SimpleNode {

    public ASTMulNode(int id) {
        super(id);
    }

    public  Object interpret() throws TMLExpressionException {
        // System.out.println("in ASTAddNode()");
        Object a = jjtGetChild(0).interpret();
        // System.out.println("Got first argument");
        Object b = jjtGetChild(1).interpret();

        // System.out.println("Got second argument");

        if ((a instanceof Integer) && (b instanceof Integer))
            return new Integer(((Integer) a).intValue() * ((Integer) b).intValue());
        else if ((a instanceof String) || (b instanceof String))
            throw new TMLExpressionException("Multiplication not defined for String values");
        else if (a instanceof Double && b instanceof Integer)
            return new Double(((Double) a).doubleValue() * ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return new Double(((Double) b).doubleValue() * ((Integer) a).intValue());
        else if (a instanceof Double && b instanceof Double)
            return new Double(((Double) b).doubleValue() * ((Double) a).doubleValue());
        else if (a instanceof Money || b instanceof Money)
            return new Money(Utils.getDoubleValue(a) * Utils.getDoubleValue(b));
          else if (a instanceof Percentage || b instanceof Percentage)
              return new Money(Utils.getDoubleValue(a) * Utils.getDoubleValue(b));
        else
            throw new TMLExpressionException("Unknown type");
    }

}
