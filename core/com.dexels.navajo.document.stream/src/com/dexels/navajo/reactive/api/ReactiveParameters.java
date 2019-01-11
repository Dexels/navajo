package com.dexels.navajo.reactive.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class ReactiveParameters {
	
	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveParameters.class);


	public final Map<String, ContextExpression> named;
	public final List<ContextExpression> unnamed;
	private final ParameterValidator validator;

//	Map<String, ContextExpression> namedParams, List<ContextExpression> unnamedParams
	private ReactiveParameters(ParameterValidator validator, Map<String,ContextExpression> namedParameters,List<ContextExpression> unnamedParameters) {
		this.validator = validator;
		this.named = namedParameters;
		this.unnamed = unnamedParameters;
	}
	
	public ReactiveResolvedParameters resolve(StreamScriptContext context, Optional<ImmutableMessage> currentMessage,ImmutableMessage paramMessage, ParameterValidator metadata) {
		
		return new ReactiveResolvedParameters(context, named,unnamed, currentMessage, paramMessage, validator);
	}
	
	public List<Operand> resolveUnnamed(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,ImmutableMessage paramMessage) {
		// TODO fix blocking
		return unnamed.stream().map(e->{
			try {
				return e.apply(context.collect().blockingGet(), currentMessage,Optional.of(paramMessage));
			} catch (Exception e1) {
				logger.error("Error applying param function: ", e1);
				return new Operand(null,"string",null);
			}
		}).collect(Collectors.toList());
	}
	
//	public List<Operand> resolveUnnamed(StreamScriptContext context ,DataItem currentMessage,DataItem paramMessage) {
//		return unnamed.stream().map(e->{
//			try {
////				Optional<ImmutableMessage> param = paramMessage.isPresent() ? Optional.of(paramMessage.get().message()) : Optional.empty();
//				return e.apply(context,  Optional.of(currentMessage.message()),paramMessage.stateMessage());
//			} catch (Exception e1) {
//				logger.error("Error applying param function: ", e1);
//				return new Operand(null,"string",null);
//			}
//		}).collect(Collectors.toList());
//	}

//	public ReactiveResolvedParameters resolveNamed(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,ImmutableMessage paramMessage, ParameterValidator validator, Optional<XMLElement> sourceElement, String sourcePath) {
//		return new ReactiveResolvedParameters(context, named, currentMessage, paramMessage,validator, sourceElement, sourcePath);
//	}

	public ReactiveParameters withConstant(String key, Object value, String type) {
		return withExpression(key, constantExpression( Operand.ofCustom(value, type)));
	}

	private ContextExpression constantExpression(final Operand value) {
		return new ContextExpression() {

			@Override
			public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					MappableTreeNode mapNode, TipiLink tipiLink, Access access,
					Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage)
					throws TMLExpressionException {
				return value;
			}

			@Override
			public boolean isLiteral() {
				return true;
			}

			@Override
			public Optional<String> returnType() {
				return Optional.of(value.type);
			}

			@Override
			public String expression() {
				return "";
			}};
	}
	public ReactiveParameters withConstant(Operand constant) {
		return withExpression(constantExpression(constant));
	}

	
	public ReactiveParameters withExpression(ContextExpression expression) {
		List<ContextExpression> list = new ArrayList<ContextExpression>(this.unnamed);
		list.add(expression);
		return new ReactiveParameters(validator, this.named, list);
	}

	public ReactiveParameters withExpression(String key, ContextExpression expression) {
		Map<String,ContextExpression> extended = new HashMap<>(named);
		extended.put(key,expression);
		return with(key,extended);
	}

	public ReactiveParameters with(String key, Map<String,ContextExpression> namedParameters) {
		Map<String,ContextExpression> extended = new HashMap<>(named);
		extended.putAll(namedParameters);
		return ReactiveParameters.of(validator, extended,this.unnamed);
	}
	
	public static ReactiveParameters of(ParameterValidator validator, Map<String,ContextExpression> namedParameters,List<ContextExpression> unnamedParameters) {
		return new ReactiveParameters(validator,namedParameters,unnamedParameters);
	}

	public static ReactiveParameters empty(ParameterValidator validator) {
		return ReactiveParameters.of(validator,Collections.emptyMap(),Collections.emptyList());
	}

}
