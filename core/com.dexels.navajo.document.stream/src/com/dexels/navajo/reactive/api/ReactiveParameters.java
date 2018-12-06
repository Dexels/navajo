package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;

import io.reactivex.functions.Function3;

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
	
	public ReactiveResolvedParameters resolve(Optional<ImmutableMessage> currentMessage,ImmutableMessage paramMessage) {
		return new ReactiveResolvedParameters(named,unnamed, currentMessage, paramMessage, validator);
	}
	
	public List<Object> resolveUnnamed(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,ImmutableMessage paramMessage) {
		// TODO fix blocking
		return unnamed.stream().map(e->{
			try {
				return e.apply(context.getInput().blockingGet(), currentMessage,Optional.of(paramMessage));
			} catch (Exception e1) {
				logger.error("Error applying param function: ", e1);
				return new Operand(null,"string",null);
			}
		}).collect(Collectors.toList());
	}
	
	public List<Operand> resolveUnnamed(StreamScriptContext context ,DataItem currentMessage,DataItem paramMessage) {
		return unnamed.stream().map(e->{
			try {
//				Optional<ImmutableMessage> param = paramMessage.isPresent() ? Optional.of(paramMessage.get().message()) : Optional.empty();
				return e.apply(context,  Optional.of(currentMessage.message()),paramMessage.stateMessage());
			} catch (Exception e1) {
				logger.error("Error applying param function: ", e1);
				return new Operand(null,"string",null);
			}
		}).collect(Collectors.toList());
	}

	public ReactiveResolvedParameters resolveNamed(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,ImmutableMessage paramMessage, ParameterValidator validator, Optional<XMLElement> sourceElement, String sourcePath) {
		return new ReactiveResolvedParameters(context, named, currentMessage, paramMessage,validator, sourceElement, sourcePath);
	}

	public ReactiveParameters withConstant(String key, Object value, String type) {
		return with(key,(context,input,param)->new Operand(value,type,null));
	}
	public ReactiveParameters with(String key, Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand> namedParam) {
		Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> extended = new HashMap<>(named);
		extended.put(key, namedParam);
		return ReactiveParameters.of(extended);
	}
	
	public static ReactiveParameters of(Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> namedParameters,List<Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> unnamedParameters) {
		return new ReactiveParameters(namedParameters, unnamedParameters);
	}

	public static ReactiveParameters of(Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> namedParameters) {
		return new ReactiveParameters(namedParameters, Collections.emptyList());
	}
	
	public static ReactiveParameters empty() {
		return ReactiveParameters.of(Collections.emptyMap());
	}

}
