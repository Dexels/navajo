package com.dexels.navajo.parser;


public final class ASTTrueNode extends SimpleNode {
    public ASTTrueNode(int id) {
        super(id);
    }

    @Override
	public final Object interpret() {
        return Boolean.TRUE;
    }

}
