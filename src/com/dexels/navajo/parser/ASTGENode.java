package com.dexels.navajo.parser;


import java.util.*;
import com.dexels.navajo.util.*;


public class ASTGENode extends SimpleNode {
    public ASTGENode(int id) {
        super(id);
    }

    public Boolean compare(Object a, Object b) throws TMLExpressionException {
        if (a instanceof Integer && b instanceof Integer)
            return new Boolean(((Integer) a).intValue() >= ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return new Boolean(((Integer) a).intValue() >= ((Double) b).doubleValue());
        else if (a instanceof Double && b instanceof Integer)
            return new Boolean(((Double) a).intValue() >= ((Integer) b).doubleValue());
        else if (a instanceof Double && b instanceof Double)
            return new Boolean(((Double) a).doubleValue() >= ((Double) b).doubleValue());
        else if (a instanceof Date) {
            return new Boolean(Utils.compareDates(a, b, ">="));
        } else
            throw new TMLExpressionException("Illegal comparison for ge; " + a.getClass().getName() + " " + b.getClass().getName());
    }

    public Object interpret() throws TMLExpressionException {

        Object a = (Object) jjtGetChild(0).interpret();


        Object b = (Object) jjtGetChild(1).interpret();


        if (a instanceof java.util.ArrayList) { // Compare all elements in the list.
            ArrayList list = (ArrayList) a;
            boolean result = true;

            for (int i = 0; i < list.size(); i++) {
                boolean dum = compare(list.get(i), b).booleanValue();


                if (dum == false)
                    return new Boolean(false);
                result = result && dum;
            }
            return new Boolean(result);
        } else {
            return compare(a, b);
        }
    }
}
