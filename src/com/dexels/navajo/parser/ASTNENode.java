package com.dexels.navajo.parser;




public final class ASTNENode extends SimpleNode {
    public ASTNENode(int id) {
        super(id);
    }

    public final Object interpret() throws TMLExpressionException {
        // System.out.println("in ASTEQNode()");
        Object a = (Object) jjtGetChild(0).interpret();
        // System.out.println("Got first argument");
        Object b = (Object) jjtGetChild(1).interpret();

        // System.out.println("Got second argument");

        return new Boolean(!Utils.equals(a, b));

    }
}
