package com.dexels.navajo.parser;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class NamedExpression implements ContextExpression {
	public final String name;
	public final ContextExpression expression;

	public NamedExpression(String name, ContextExpression expression) {
		this.name = name;
		this.expression = expression;
	}

	@Override
	public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
			Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
		return expression.apply(doc,parentMsg,parentParamMsg,parentSel,mapNode,tipiLink,access,immutableMessage,paramMessage);
	}

	@Override
	public boolean isLiteral() {
		return expression.isLiteral();
	}

	@Override
	public Optional<String> returnType() {
		return expression.returnType();
	}

	@Override
	public String expression() {
		return expression.expression();
	}
}
