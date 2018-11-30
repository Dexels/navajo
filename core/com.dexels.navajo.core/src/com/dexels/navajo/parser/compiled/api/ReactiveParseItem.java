package com.dexels.navajo.parser.compiled.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public class ReactiveParseItem implements ContextExpression {
	public static enum ReactiveItemType {
		HEADER,SOURCE,TRANSFORMER,MAPPER
	}

	public final String name;
	public final ReactiveItemType type;
	public final Map<String, ContextExpression> namedParams;
	public final List<ContextExpression> unnamedParams;
	public final String expression;
	
	public ReactiveParseItem(String name, ReactiveItemType type, Map<String,ContextExpression> namedParams, List<ContextExpression> unnamedParams, String expression) {
		this.name = name;
		this.type = type;
		this.namedParams = namedParams;
		this.unnamedParams = unnamedParams;
		this.expression = expression;
	}

	@Override
	public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
			Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
		switch (type) {
			case SOURCE:
//				return Reactive.finderInstance().getSourceFactory(name).build(namedParams, unnamedParams);
		case HEADER:
			break;
		case MAPPER:
			return Reactive.finderInstance().getMergerFactory(name);
		case TRANSFORMER:
			return Reactive.finderInstance().getTransformerFactory(name);
		default:
			break;

		}

		return null;
	}

	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	public Optional<String> returnType() {
		return Optional.of("REACTIVE-"+type.toString());
	}

	@Override
	public String expression() {
		return expression;
	}
}
