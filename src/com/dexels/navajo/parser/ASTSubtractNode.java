package com.dexels.navajo.parser;


import java.util.*;

@SuppressWarnings("unchecked")
public final class ASTSubtractNode extends SimpleNode {
    public ASTSubtractNode(int id) {
        super(id);
    }

    public final Object interpret() throws TMLExpressionException {
        // System.out.println("in ASTAddNode()");
        Object a = jjtGetChild(0).interpret();
        // System.out.println("Got first argument");
        Object b = jjtGetChild(1).interpret();

        // System.out.println("Got second argument");

        if (!(a instanceof ArrayList || b instanceof ArrayList)) {
            return Utils.subtract(a, b);
        } else if ((a instanceof ArrayList) && !(b instanceof ArrayList)) {
            ArrayList list = (ArrayList) a;
            ArrayList result = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                Object rel = Utils.subtract(val, b);

                result.add(rel);
            }
            return result;
        } else if ((b instanceof ArrayList) && !(a instanceof ArrayList)) {
            ArrayList list = (ArrayList) b;
            ArrayList result = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                Object rel = Utils.subtract(a, val);

                result.add(rel);
            }
            return result;
        } else if (a instanceof ArrayList && b instanceof ArrayList) {
            ArrayList list1 = (ArrayList) a;
            ArrayList list2 = (ArrayList) b;

            if (list1.size() != list2.size())
                throw new TMLExpressionException("Can only add lists of equals length");
            ArrayList result = new ArrayList();

            for (int i = 0; i < list1.size(); i++) {
                Object val1 = list1.get(i);
                Object val2 = list2.get(i);
                Object rel = Utils.subtract(val1, val2);

                result.add(rel);
            }
            return result;
        } else
            throw new TMLExpressionException("Unknown type");
    }
}
