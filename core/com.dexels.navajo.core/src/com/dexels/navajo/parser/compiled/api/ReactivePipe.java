package com.dexels.navajo.parser.compiled.api;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class ReactivePipe implements ContextExpression {
	public final ContextExpression source;
	public final List<ContextExpression> transformers;

	public ReactivePipe(ContextExpression source, List<ContextExpression> transformers) {
		this.source = source;
		this.transformers = transformers;
	}

	@Override
	public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
			Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
//		ReactiveSourceFactory rsf = (ReactiveSourceFactory) source.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage, paramMessage);
		throw new UnsupportedOperationException("Should not be called!");
	}

	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	public Optional<String> returnType() {
		return Optional.of("REACTIVEPIPE");
	}

	@Override
	public String expression() {
		return "some_reactive_expression";
	}
}
