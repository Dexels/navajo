package com.dexels.navajo.parser;


public class ASTIntegerConstantNode extends SimpleNode {

    int val;

    public ASTIntegerConstantNode(int id) {
        super(id);
    }

    public  Object interpret() {
        return new Integer(val);
    }
}
