package com.dexels.navajo.parser;


import java.util.*;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Percentage;


public final class ASTLENode extends SimpleNode {
    public ASTLENode(int id) {
        super(id);
    }

    public final static Boolean compare(Object a, Object b) throws TMLExpressionException {
        if (a instanceof Integer && b instanceof Integer)
            return Boolean.valueOf(((Integer) a).intValue() <= ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return Boolean.valueOf(((Integer) a).intValue() <= ((Double) b).doubleValue());
        else if (a instanceof Double && b instanceof Integer)
            return Boolean.valueOf(((Double) a).intValue() <= ((Integer) b).doubleValue());
        else if (a instanceof Double && b instanceof Double)
            return Boolean.valueOf(((Double) a).doubleValue() <= ((Double) b).doubleValue());
        else if (a instanceof Date)
            return Boolean.valueOf(Utils.compareDates(a, b, "<="));
        else if (a instanceof Money || b instanceof Money)
            return Boolean.valueOf(Utils.getDoubleValue(a) <= Utils.getDoubleValue(b));
          else if (a instanceof Percentage || b instanceof Percentage)
              return Boolean.valueOf(Utils.getDoubleValue(a) <= Utils.getDoubleValue(b));
         else if (a instanceof ClockTime && b instanceof ClockTime)
           return Boolean.valueOf(Utils.compareDates(a, b, "<="));
        else
            throw new TMLExpressionException("Illegal comparison for gt; " + a.getClass().getName() + " " + b.getClass().getName());
    }

    public final Object interpret() throws TMLExpressionException {
        // System.out.println("in ASTGENode()");
        Object a = (Object) jjtGetChild(0).interpret();
        // System.out.println("Got first argument");
        Object b = (Object) jjtGetChild(1).interpret();

        // System.out.println("Got second argument");

        if (a instanceof java.util.ArrayList) { // Compare all elements in the list.
            ArrayList list = (ArrayList) a;
            boolean result = true;

            for (int i = 0; i < list.size(); i++) {
                boolean dum = compare(list.get(i), b).booleanValue();

                if (dum == false)
                    return Boolean.valueOf(false);
                result = result && dum;
            }
            return Boolean.valueOf(result);
        } else {
            return compare(a, b);
        }
    }
}
