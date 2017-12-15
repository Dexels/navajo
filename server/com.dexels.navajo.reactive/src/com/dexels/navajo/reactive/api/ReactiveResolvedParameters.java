package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.functions.Function3;

public class ReactiveResolvedParameters {

	public final Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> named;
	Map<String,Operand> result = new HashMap<>();
	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveResolvedParameters.class);
	private final StreamScriptContext context;
	private boolean allResolved = false;
	private final Optional<ImmutableMessage> currentMessage;
	private final Optional<ImmutableMessage> paramMessage;
	private final Optional<Map<String,String>> expectedTypes;
	private final XMLElement sourceElement;
	private final String sourcePath;
	private final Map<String, Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> > defaultExpressions;
	
	public ReactiveResolvedParameters(StreamScriptContext context, Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> named,Optional<ImmutableMessage> currentMessage,Optional<ImmutableMessage> paramMessage, ParameterValidator validator, XMLElement sourceElement, String sourcePath,Map<String, Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> > defaultExpressions) {
		this.named = named;
		this.context = context;
		this.currentMessage = currentMessage;
		this.paramMessage = paramMessage;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.defaultExpressions = defaultExpressions;
		Optional<List<String>> allowed = validator.allowedParameters();
		Optional<List<String>> required = validator.requiredParameters();

		if(allowed.isPresent()) {
			List<String> al = allowed.get();
			named.entrySet().forEach(e->{
				if(!al.contains(e.getKey())) {
					throw new ReactiveParameterException("Parameter name: "+e.getKey()+" is not allowed for this entity. Allowed entities: "+al+" file: "+sourcePath+" line: "+sourceElement.getStartLineNr());
				}
			});
		}
		if(required.isPresent()) {
			List<String> req = required.get();
			for (String requiredParam : req) {
				if(!named.containsKey(requiredParam)) {
					if(!defaultExpressions.containsKey(requiredParam)) {
						throw new ReactiveParameterException("Missing parameter name: "+requiredParam+". Supplied params: "+named.keySet()+" file: "+sourcePath+" line: "+sourceElement.getStartLineNr());
					}
				}
			}
		}
		expectedTypes = validator.parameterTypes();
	}

	public Map<String,Operand> resolveAllParams() {
		if(!allResolved) {
			resolveNamed();
		}
		return Collections.unmodifiableMap(result);
	}
	
	public Optional<Operand> paramValue(String key) {
		if(result.containsKey(key)) {
			return Optional.of(result.get(key));
		}
		Optional<String> expectedType = expectedTypes.isPresent() ? Optional.ofNullable(expectedTypes.get().get(key)) : Optional.empty();
		Function3<StreamScriptContext, Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> function = named.get(key);
		if(function==null) {
			return Optional.empty();
		}
		return Optional.of(resolveParam(key,expectedType, function));
	}
	

	private Object getCheckedValue(String expectedType, String key, Optional<Operand> res, Optional<Object> defaultValue) {
		if(!res.isPresent()) {
			Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> def = defaultExpressions.get(key);
			if(def!=null) {
				try {
					Operand defaultOperand = def.apply(context, currentMessage, paramMessage);
					if(!expectedType.equals(defaultOperand.type)) {
						throw new ReactiveParameterException("Error evaluating default expression for key: "+key+" it is not of the expected type: "+expectedType+" but of type: "+defaultOperand.type+", which is demanded by the calling code"+" file: "+sourcePath+" line: "+sourceElement.getStartLineNr());
					}
					return defaultOperand.value;
				} catch (Exception e) {
					throw new ReactiveParameterException("Error evaluating default expression for key: "+key,e);

				}
			}
			if(defaultValue.isPresent()) {
				return defaultValue.get();
			}
			throw new ReactiveParameterException("Error evaluating required key: "+key+" it is missing.");
		}
		Operand result = res.get();
		if(!expectedType.equals(result.type)) {
			throw new ReactiveParameterException("Error evaluating key: "+key+" it is not of the expected type: "+expectedType+" but of type: "+result.type+", which is demanded by the calling code"+" file: "+sourcePath+" line: "+sourceElement.getStartLineNr());
		}
		return  result.value;
	}
	
	public String paramString(String key) {
		return (String)getCheckedValue(Property.STRING_PROPERTY, key, paramValue(key), Optional.empty());
	}

	
	public String paramString(String key,String defaultValue) {
		return (String)getCheckedValue(Property.STRING_PROPERTY, key, paramValue(key), Optional.of(defaultValue));
	}

	public int paramInteger(String key) {
		return (Integer)getCheckedValue(Property.INTEGER_PROPERTY, key, paramValue(key), Optional.empty());
	}
	
	public int paramInteger(String key, int defaultValue) {
		return (Integer)getCheckedValue(Property.INTEGER_PROPERTY, key, paramValue(key), Optional.of(defaultValue));
	}
	
	public boolean paramBoolean(String key) {
		return (Boolean)getCheckedValue(Property.BOOLEAN_PROPERTY, key, paramValue(key), Optional.empty());
	}
	
	public boolean paramBoolean(String key, boolean defaultValue) {
		return (Boolean)getCheckedValue(Property.BOOLEAN_PROPERTY, key, paramValue(key), Optional.of(defaultValue));
	}
	
	
	private void resolveNamed() {
		named.entrySet().forEach(e->{
			Optional<String> expectedType = expectedTypes.isPresent() ? Optional.ofNullable(expectedTypes.get().get(e.getKey())) : Optional.empty();
			resolveParam(e.getKey(),expectedType,e.getValue());
		});
		allResolved = true;
	}
	

	private Operand resolveParam(String key,Optional<String> expectedType, Function3<StreamScriptContext, Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> function) {
		Operand applied;
		try {
			applied = function.apply(context, currentMessage,paramMessage);
			result.put(key, applied);
			
			if(expectedType.isPresent() && !applied.type.equals(expectedType.get())) {
				throw new ReactiveParameterException("Error evaluating key: "+key+" it is not of the expected type: "+expectedType.get()+" but of type: "+applied.type+" with value: "+applied.value+" path: "+sourcePath+" element: "+sourceElement+" -> "+ sourceElement.getStartLineNr());
			}
			return applied;
		} catch (Exception e1) {
			logger.error("Error applying param function for named param: "+key+" will put null.", e1);
			throw new ReactiveParameterException("Error applying param function for named param: "+key,e1);
		}
	}

}
