/* Generated By:JJTree&JavaCC: Do not edit this line. ASTIntegerConstantNode.java */
package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTIntegerConstantNode extends SimpleNode {

    int val;

    public ASTIntegerConstantNode(int id) {
        super(id);
    }

	public final  Object interpret() {
        return new Integer(val);
    }

	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression) {
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return true;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg,
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
