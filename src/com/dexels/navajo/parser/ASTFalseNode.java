package com.dexels.navajo.parser;


public class ASTFalseNode extends SimpleNode {
    public ASTFalseNode(int id) {
        super(id);
    }

    public final Object interpret() {
        return new Boolean(false);
    }

}
