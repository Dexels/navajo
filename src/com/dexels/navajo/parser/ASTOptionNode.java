package com.dexels.navajo.parser;


public class ASTOptionNode extends SimpleNode {

    String option = "";

    public ASTOptionNode(int id) {
        super(id);
        // System.out.println("in ASTOptionNode()");
    }

    public Object interpret() {
        Node optionParent = this.jjtGetParent();

        // System.out.println("PARENT = " + optionParent);
        // System.out.println("OPTION = " + option);
        return new String(option);
    }
}
