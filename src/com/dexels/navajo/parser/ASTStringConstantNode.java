package com.dexels.navajo.parser;


public final class ASTStringConstantNode extends SimpleNode {

    String val;

    public ASTStringConstantNode(int id) {
        super(id);
    }

    public final Object interpret() {
        // Strip quotes.
        String s = val.substring(1, val.length() - 1);
        String t = s.replaceAll("\\\\'","'");
        return t;
//        return new String(val.substring(1, val.length() - 1));
    }
}
