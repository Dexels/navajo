package com.dexels.navajo.parser;


public class ASTDatePatternNode extends SimpleNode {
    public ASTDatePatternNode(int id) {
        super(id);
    }

    public Object interpret() throws TMLExpressionException {
        Object y = (Object) jjtGetChild(0).interpret();
        Object m = (Object) jjtGetChild(1).interpret();
        Object d = (Object) jjtGetChild(2).interpret();
        Object h = (Object) jjtGetChild(3).interpret();
        Object min = (Object) jjtGetChild(4).interpret();
        Object s = (Object) jjtGetChild(5).interpret();

        if (!((y instanceof Integer) && (m instanceof Integer) && (d instanceof Integer) && (h instanceof Integer) && (min instanceof Integer) && (s instanceof Integer)))
             throw new TMLExpressionException("Integer arguments expected in date pattern: (" + y + "," + m + "," + d + "," + h + "," + min + "," + s +")");

        int yearT = ((Integer) y).intValue();
        int monthT = ((Integer) m).intValue();
        int dayT = ((Integer) d).intValue();
        int hourT = ((Integer) h).intValue();
        int minT = ((Integer) min).intValue();
        int secT = ((Integer) s).intValue();
        return new DatePattern(yearT, monthT, dayT, hourT, minT, secT, true);
    }

}
