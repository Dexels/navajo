package com.dexels.navajo.parser;

@SuppressWarnings("unused")
public final class ASTOptionNode extends SimpleNode {

    String option = "";

    public ASTOptionNode(int id) {
        super(id);
        // System.out.println("in ASTOptionNode()");
    }

    public final Object interpret() {
       
		Node optionParent = this.jjtGetParent();

        // System.out.println("PARENT = " + optionParent);
        // System.out.println("OPTION = " + option);
        return option;
    }
}
