/* Generated By:JJTree&JavaCC: Do not edit this line. ASTTodayNode.java */
package com.dexels.navajo.parser;


import java.util.Date;


public final class ASTTodayNode extends SimpleNode {
    public ASTTodayNode(int id) {
        super(id);
    }

    @Override
	public final Object interpret() {
        return new Date();
    }
}
