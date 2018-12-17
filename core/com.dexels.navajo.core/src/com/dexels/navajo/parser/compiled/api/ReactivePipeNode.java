package com.dexels.navajo.parser.compiled.api;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class ReactivePipeNode implements ContextExpression {
	public final ReactiveSource source;
	public final List<Object> transformers;

	public ReactivePipeNode(ReactiveSource source, List<Object> transformers) {
		this.source = source;
		this.transformers = transformers;
	}

	@Override
	public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
			Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
		return new Operand(new ReactivePipe(source, transformers),Reactive.ReactiveItemType.REACTIVE_PIPE.toString());
	}

	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	public Optional<String> returnType() {
		return Optional.of(Reactive.ReactiveItemType.REACTIVE_PIPE.toString());
	}

	@Override
	public String expression() {
		return "some_reactive_expression";
	}
}
