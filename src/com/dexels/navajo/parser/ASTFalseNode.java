package com.dexels.navajo.parser;


public class ASTFalseNode extends SimpleNode {
    public ASTFalseNode(int id) {
        super(id);
    }

    public Object interpret() {
        return new Boolean(false);
    }

}
