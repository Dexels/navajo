package com.dexels.navajo.parser;


public final class ASTDatePatternNode extends SimpleNode {
    public ASTDatePatternNode(int id) {
        super(id);
    }

    @Override
	public final Object interpret() throws TMLExpressionException {
        Object y = jjtGetChild(0).interpret();
        Object m = jjtGetChild(1).interpret();
        Object d = jjtGetChild(2).interpret();
        Object h = jjtGetChild(3).interpret();
        Object min = jjtGetChild(4).interpret();
        Object s = jjtGetChild(5).interpret();

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
