package com.dexels.navajo.parser;


public class ASTAndNode extends SimpleNode {
    public ASTAndNode(int id) {
        super(id);
    }

    public Object interpret() throws TMLExpressionException {

        Boolean a = (Boolean) jjtGetChild(0).interpret();

        if (a.booleanValue() == false)
            return new Boolean(false);


        Boolean b = (Boolean) jjtGetChild(1).interpret();



        return new Boolean(a.booleanValue() && b.booleanValue());
    }
}
