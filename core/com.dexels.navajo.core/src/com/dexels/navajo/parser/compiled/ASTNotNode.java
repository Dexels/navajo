/* Generated By:JJTree&JavaCC: Do not edit this line. ASTNotNode.java */

package com.dexels.navajo.parser.compiled;

import com.dexels.navajo.parser.TMLExpressionException;

public final class ASTNotNode extends SimpleNode {
    public ASTNotNode(int id) {
        super(id);
    }
	@Override
	public ContextExpression interpretToLambda() {
		return lazyFunction(a->interpret(a));
	}

	public final Object interpret(Object a) {

        
        if (!(a instanceof Boolean)) {
            throw new TMLExpressionException("Not operator only allowed for Boolean values");
        } else {
            Boolean b = (Boolean) a;
            return Boolean.valueOf(!b.booleanValue());
        }
    }

}