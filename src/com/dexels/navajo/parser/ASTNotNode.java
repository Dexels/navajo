/* Generated By:JJTree: Do not edit this line. ASTNotNode.java */

package com.dexels.navajo.parser;


public final class ASTNotNode extends SimpleNode {
    public ASTNotNode(int id) {
        super(id);
    }

    public final Object interpret() throws TMLExpressionException {

        Object a = this.jjtGetChild(0).interpret();

        if (!(a instanceof Boolean)) {
            throw new TMLExpressionException("Not operator only allowed for Boolean values");
        } else {
            Boolean b = (Boolean) a;

            return new Boolean(!b.booleanValue());
        }
    }

}
