package com.dexels.navajo.parser;


public class ASTFloatConstantNode extends SimpleNode {

    double val;

    public ASTFloatConstantNode(int id) {
        super(id);
    }

    public Object interpret() {

        return new Double(val);
    }

}
