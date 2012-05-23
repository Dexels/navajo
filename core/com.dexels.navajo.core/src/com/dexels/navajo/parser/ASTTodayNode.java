package com.dexels.navajo.parser;


import java.util.Date;


public final class ASTTodayNode extends SimpleNode {
    public ASTTodayNode(int id) {
        super(id);
    }

    public final Object interpret() {
        return new Date();
    }
}
