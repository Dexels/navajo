/* Generated By:JJTree&JavaCC: Do not edit this line. ASTNotNode.java */

package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;

public final class ASTNotNode extends SimpleNode {
    public ASTNotNode(int id) {
        super(id);
    }
	@Override
	public ContextExpression interpretToLambda(List<String> problems) {
		
		return lazyFunction(problems, a->interpret(a),Optional.of(Property.BOOLEAN_PROPERTY));
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
