package com.dexels.navajo.parser;


public final class ASTFalseNode extends SimpleNode {
    public ASTFalseNode(int id) {
        super(id);
    }

    public final Object interpret() {
        return new Boolean(false);
    }

}
