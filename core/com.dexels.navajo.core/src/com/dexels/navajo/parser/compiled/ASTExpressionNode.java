/* Generated By:JJTree&JavaCC: Do not edit this line. ASTExpressionNode.java */

package com.dexels.navajo.parser.compiled;

import java.util.List;

import com.dexels.navajo.parser.compiled.api.ContextExpression;

public final class ASTExpressionNode extends SimpleNode {
    public ASTExpressionNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda(List<String> problems) {
		return  jjtGetChild(0).interpretToLambda(problems);
	}

}
