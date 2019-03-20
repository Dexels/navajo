/* Generated By:JJTree&JavaCC: Do not edit this line. ASTIntegerConstantNode.java */
package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

final class ASTIntegerConstantNode extends SimpleNode {

    int val;

    ASTIntegerConstantNode(int id) {
        super(id);
    }

	private final  Operand interpret() {
        return Operand.ofInteger(val);
    }

	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return true;
			}
			
			@Override
			public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg,
					Selection parentSel,  MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
				return interpret();
			}

			@Override
			public Optional<String> returnType() {
				return Optional.of(Property.INTEGER_PROPERTY);
			}
			
			@Override
			public String expression() {
				return expression;
			}
		};
	}
}
