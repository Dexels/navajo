package com.dexels.navajo.parser;


public class ASTAndNode extends SimpleNode {
    public ASTAndNode(int id) {
        super(id);
    }

    public Object interpret() throws TMLExpressionException {
        com.dexels.navajo.util.Util.debugLog("in ASTAndNode()");
        Boolean a = (Boolean) jjtGetChild(0).interpret();

        if (a.booleanValue() == false)
            return new Boolean(false);

        com.dexels.navajo.util.Util.debugLog("Got first argument");
        Boolean b = (Boolean) jjtGetChild(1).interpret();

        com.dexels.navajo.util.Util.debugLog("Got second argument");

        return new Boolean(a.booleanValue() && b.booleanValue());
    }
}
