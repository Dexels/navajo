package com.dexels.navajo.parser;


public class ASTTrueNode extends SimpleNode {
    public ASTTrueNode(int id) {
        super(id);
    }

    public final Object interpret() {
        return new Boolean(true);
    }

}
