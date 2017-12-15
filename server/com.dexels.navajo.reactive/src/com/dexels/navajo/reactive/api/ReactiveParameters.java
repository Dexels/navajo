package com.dexels.navajo.reactive.api;

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

import io.reactivex.functions.Function3;

public class ReactiveParameters {
	
	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveParameters.class);


	public final Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> named;
	public final List<Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> unnamed;

	private final Map<String, Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> > defaultExpressions;

	private ReactiveParameters(Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> namedParameters,List<Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> unnamedParameters, Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> > defaultExpressions) {
		this.named = namedParameters;
		this.unnamed = unnamedParameters;
		this.defaultExpressions = defaultExpressions;

	}

	public List<Operand> resolveUnnamed(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,Optional<ImmutableMessage> paramMessage) {
		return unnamed.stream().map(e->{
			try {
				return e.apply(context, currentMessage,paramMessage);
			} catch (Exception e1) {
				logger.error("Error applying param function: ", e1);
				return new Operand(null,"string",null);
			}
		}).collect(Collectors.toList());
	}
	
	public List<Operand> resolveUnnamed(StreamScriptContext context ,DataItem currentMessage,Optional<DataItem> paramMessage) {
		return unnamed.stream().map(e->{
			try {
				Optional<ImmutableMessage> param = paramMessage.isPresent() ? Optional.of(paramMessage.get().message()) : Optional.empty();
				return e.apply(context,  Optional.of(currentMessage.message()),param);
			} catch (Exception e1) {
				logger.error("Error applying param function: ", e1);
				return new Operand(null,"string",null);
			}
		}).collect(Collectors.toList());
	}

	public ReactiveResolvedParameters resolveNamed(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,Optional<ImmutableMessage> paramMessage, ParameterValidator validator, XMLElement sourceElement, String sourcePath) {

		return new ReactiveResolvedParameters(context, named, currentMessage, paramMessage,validator, sourceElement, sourcePath,defaultExpressions);
	}
	
	@Deprecated
	private Map<String,Operand> resolveNamedOld(StreamScriptContext context ,Optional<ImmutableMessage> currentMessage,Optional<ImmutableMessage> paramMessage) {
		Map<String,Operand> result = new HashMap<>();
		named.entrySet().forEach(e->{
			Operand applied;
			try {
				applied = e.getValue().apply(context, currentMessage,paramMessage);
				result.put(e.getKey(), applied);
			} catch (Exception e1) {
				logger.error("Error applying param function for named param: "+e.getKey()+" will put null.", e1);
				result.put(e.getKey(), null);
			}
		});
		return result;
	}

	@Deprecated
	public Map<String,Operand> resolveNamed(StreamScriptContext context ,DataItem currentMessage,Optional<DataItem> paramMessage) {
		Optional<ImmutableMessage> param = paramMessage.isPresent() ? Optional.of(paramMessage.get().message()) : Optional.empty();
		Optional<ImmutableMessage> current = Optional.of(currentMessage.message());
		return resolveNamedOld(context, current, param);
	}
	
	public static ReactiveParameters of(Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> namedParameters,List<Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> unnamedParameters, Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> > defaultExpressions) {
		return new ReactiveParameters(namedParameters, unnamedParameters,defaultExpressions);
	}

	
}
