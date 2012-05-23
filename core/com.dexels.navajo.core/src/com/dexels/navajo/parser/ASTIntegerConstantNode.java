package com.dexels.navajo.parser;


public final class ASTIntegerConstantNode extends SimpleNode {

    int val;

    public ASTIntegerConstantNode(int id) {
        super(id);
    }

    public final  Object interpret() {
        return new Integer(val);
    }
}
