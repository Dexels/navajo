package com.dexels.navajo.parser.compiled.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

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
		return materializeReactive();
	}

	private Object materializeReactive() {
		switch (type) {
		case SOURCE:
			ReactiveSourceFactory sourceFactory = Reactive.finderInstance().getSourceFactory(name);
			return sourceFactory.build(ReactiveParameters.of(sourceFactory, namedParams, unnamedParams));
		case HEADER:
			break;
		case MAPPER:
			ReactiveMerger mergerFactory = Reactive.finderInstance().getMergerFactory(name);
			ReactiveParameters mergeParameters = ReactiveParameters.of(mergerFactory, namedParams, unnamedParams);
			return mergerFactory.execute(mergeParameters);
		case TRANSFORMER:
			ReactiveTransformerFactory transformerFactory = Reactive.finderInstance().getTransformerFactory(name);
			ReactiveParameters transParameters = ReactiveParameters.of(transformerFactory, namedParams, unnamedParams);
			List<ReactiveParseProblem> problems = new ArrayList<>();
			// TODO problems?
			return transformerFactory.build(problems, transParameters);
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
