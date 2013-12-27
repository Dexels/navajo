package com.dexels.navajo.parser;


public final class ASTFalseNode extends SimpleNode {
    public ASTFalseNode(int id) {
        super(id);
    }

    @Override
	public final Object interpret() {
        return Boolean.valueOf(false);
    }

}
