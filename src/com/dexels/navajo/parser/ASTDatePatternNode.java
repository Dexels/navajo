package com.dexels.navajo.parser;


public class ASTDatePatternNode extends SimpleNode {
    public ASTDatePatternNode(int id) {
        super(id);
    }

    public Object interpret() throws TMLExpressionException {
        Object y = (Object) jjtGetChild(0).interpret();
        Object m = (Object) jjtGetChild(1).interpret();
        Object d = (Object) jjtGetChild(2).interpret();
        if (!((y instanceof Integer) && (m instanceof Integer) && (d instanceof Integer)))
          throw new TMLExpressionException("Integer arguments expected in date pattern: (" + y + "," + m + "," + d + ")");
        int yearT = ((Integer) y).intValue();
        int monthT = ((Integer) m).intValue();
        int dayT = ((Integer) d).intValue();
        return new DatePattern(yearT, monthT, dayT, true);
    }

}
