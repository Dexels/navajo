package com.dexels.navajo.parser;


public class ASTStringConstantNode extends SimpleNode {

    String val;

    public ASTStringConstantNode(int id) {
        super(id);
    }

    public final Object interpret() {
        // Strip quotes.
        return new String(val.substring(1, val.length() - 1));
    }
}
