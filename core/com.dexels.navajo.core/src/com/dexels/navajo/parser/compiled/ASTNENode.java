/* Generated By:JJTree&JavaCC: Do not edit this line. ASTNENode.java */

package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.parser.Utils;

public final class ASTNENode extends SimpleNode {
    public ASTNENode(int id) {
        super(id);
    }
	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		return lazyBiFunction(problems,expression, (a,b)->interpret(a, b,expression),equalOrEmptyTypes(),(a,b)->Optional.of(Property.BOOLEAN_PROPERTY),functionClassifier);
	}
	
	public final Operand interpret(Operand a, Operand b, String expression) {

        return Operand.ofBoolean(!Utils.equals(a.value, b.value,expression));

    }
}
