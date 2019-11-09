package com.dexels.navajo.parser.compiled.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.parser.compiled.Node;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class ReactiveParseItem implements ContextExpression {


	private final String name;
	private final Reactive.ReactiveItemType type;
	private final Map<String, ContextExpression> namedParams;
	private final List<ContextExpression> unnamedParams;
	private final String expression;
	private final Node node;
	
	public ReactiveParseItem(String name, Reactive.ReactiveItemType type, Map<String,ContextExpression> namedParams, List<ContextExpression> unnamedParams, String expression, Node node) {
		this.name = name;
		this.type = type;
		this.namedParams = namedParams;
		this.unnamedParams = unnamedParams;
		this.expression = expression;
		this.node = node;
	}

	@Override
	public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
			Optional<ImmutableMessage> paramMessage) {
		return materializeReactive();
	}

	private Operand materializeReactive() {
		switch (type) {
		case REACTIVE_SOURCE:
			ReactiveSourceFactory sourceFactory = Reactive.finderInstance().getSourceFactory(name);
			if(sourceFactory==null) {
				throw new ReactiveParseException("No source found named: "+name);
			}
			return new Operand(sourceFactory.build(ReactiveParameters.of(sourceFactory, namedParams, unnamedParams)),Reactive.ReactiveItemType.REACTIVE_MAPPER.toString());
		case REACTIVE_HEADER:
			break;
		case REACTIVE_MAPPER:
			ReactiveMerger mergerFactory = Reactive.finderInstance().getMergerFactory(name);
			ReactiveParameters mergeParameters = ReactiveParameters.of(mergerFactory, namedParams, unnamedParams);
			return new Operand(mergerFactory.execute(mergeParameters), Reactive.ReactiveItemType.REACTIVE_MAPPER.toString());
		case REACTIVE_TRANSFORMER:
			ReactiveTransformerFactory transformerFactory = Reactive.finderInstance().getTransformerFactory(name);
			List<ReactiveParseProblem> problems = new ArrayList<>();
			ReactiveParameters transParameters = ReactiveParameters.of(transformerFactory, namedParams, unnamedParams);
			// TODO problems?
			return new Operand(transformerFactory.build(problems, transParameters),Reactive.ReactiveItemType.REACTIVE_MAPPER.toString());
		default:
			break;

		}
		// TODO rather throw something
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
