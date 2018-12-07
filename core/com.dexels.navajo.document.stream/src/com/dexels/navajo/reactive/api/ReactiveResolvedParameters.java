package com.dexels.navajo.reactive.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.ContextExpression;

import io.reactivex.Single;

public class ReactiveResolvedParameters {

//	public final Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> named;
	Map<String,Object> resolvedNamed = new HashMap<>();
	List<Object> resolvedUnnamed = new ArrayList<>();
	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveResolvedParameters.class);
//	private final StreamScriptContext context;
	private boolean allResolved = false;
	private final Optional<ImmutableMessage> currentMessage;
	private final ImmutableMessage paramMessage;
	private final Optional<Map<String,String>> expectedTypes;

	private final Map<String, ContextExpression> named;
//	private final Optional<XMLElement> sourceElement;
//	private final String sourcePath;

	private final List<ContextExpression> unnamed;

	private final Single<Navajo> input;
	
	public ReactiveResolvedParameters(Single<Navajo> single, Map<String, ContextExpression> named, List<ContextExpression> unnamed,
			Optional<ImmutableMessage> currentMessage, ImmutableMessage paramMessage, ParameterValidator validator) {
		this.currentMessage = currentMessage;
		this.paramMessage = paramMessage;
		this.named = named;
		this.unnamed = unnamed;
		this.input = single;
		Optional<List<String>> allowed = validator.allowedParameters();
		Optional<List<String>> required = validator.requiredParameters();

		if(allowed.isPresent()) {
			List<String> al = allowed.get();
			named.entrySet().forEach(e->{
				if(!al.contains(e.getKey())) {
					throw new ReactiveParameterException("Parameter name: "+e.getKey()+" is not allowed for this entity. Allowed entities: "+al+"");
				}
			});
		}
		if(required.isPresent()) {
			List<String> req = required.get();
			for (String requiredParam : req) {
				if(!named.containsKey(requiredParam)) {
					throw new ReactiveParameterException("Missing parameter name: "+requiredParam+". Supplied params: "+named.keySet());
				}
			}
		}
		expectedTypes = validator.parameterTypes();		// TODO Auto-generated constructor stub
	}

//	public ReactiveResolvedParameters(StreamScriptContext context, Map<String,Function3<StreamScriptContext,
//			Optional<ImmutableMessage>,ImmutableMessage,Operand>> named,
//			Optional<ImmutableMessage> currentMessage,
//			ImmutableMessage paramMessage, ParameterValidator validator, 
//			Optional<XMLElement> sourceElement,
//			String sourcePath) {
//		this.named = named;
//		this.context = context;
//		this.currentMessage = currentMessage;
//		this.paramMessage = paramMessage;
////		this.sourceElement = sourceElement;
////		this.sourcePath = sourcePath;
//		Optional<List<String>> allowed = validator.allowedParameters();
//		Optional<List<String>> required = validator.requiredParameters();
//
//		if(allowed.isPresent()) {
//			List<String> al = allowed.get();
//			named.entrySet().forEach(e->{
//				if(!al.contains(e.getKey())) {
//					throw new ReactiveParameterException("Parameter name: "+e.getKey()+" is not allowed for this entity. Allowed entities: "+al+" file: "+sourcePath+" line: "+sourceElement.map(xml->""+xml.getStartLineNr()).orElse("<unknown>") );
//				}
//			});
//		}
//		if(required.isPresent()) {
//			List<String> req = required.get();
//			for (String requiredParam : req) {
//				if(!named.containsKey(requiredParam)) {
//					throw new ReactiveParameterException("Missing parameter name: "+requiredParam+". Supplied params: "+named.keySet()+" file: "+sourcePath+" line: "+sourceElement.map(xml->""+xml.getStartLineNr()).orElse("<unknown>"));
//				}
//			}
//		}
//		expectedTypes = validator.parameterTypes();
//	}

//
//	public boolean hasKey(String key) {
//		return result.containsKey(key);
//	}
	
	public List<Object> unnamedParameters() {
		return this.resolvedUnnamed;
	}
	
	public Map<String,Object> resolveAllParams() {
		if(!allResolved) {
			resolveNamed();
			resolveUnnamed();
		}
		return Collections.unmodifiableMap(resolvedNamed);
	}
	
	private Optional<Object> paramValue(String key) {
		if(resolvedNamed.containsKey(key)) {
			return Optional.of(resolvedNamed.get(key));
		}
		Optional<String> expectedType = expectedTypes.isPresent() ? Optional.ofNullable(expectedTypes.get().get(key)) : Optional.empty();
		ContextExpression function = named.get(key);
		if(function==null) {
			return Optional.empty();
		}
		return Optional.of(resolveParam(key,expectedType, function));
	}
	

	private Object getCheckedValue(Optional<String> expectedType, String key, Optional<Object> res, Optional<Callable<? extends Object>> defaultValue) {
		if(!res.isPresent()) {
			if(defaultValue.isPresent()) {
				try {
					return new Operand(defaultValue.get().call(),expectedType.orElse("object"),null);
				} catch (Exception e) {
					throw new ReactiveParameterException("Error evaluating required key: "+key+" it threw an exception.",e);
				}
			}
			throw new ReactiveParameterException("Error evaluating required key: "+key+" it is missing.");
		}
		return res.get();
	}
	
	private Optional<Object> typeCheckedOperand(Optional<Object> res,String key, Optional<String> expectedType) {
		return res;
//		Optional<Operand> res = paramValue(key);
//		if(res.isPresent()) {
//			return res;
//			Object p = res.get();
//			if(expectedType.isPresent()) {
//				if(expectedType.get().equals(p.type)) {
//					return res;
//				} else {
//					throw new ReactiveParameterException("Error evaluating key: "+key+" it is not of the expected type: "+expectedType+" but of type: "+p.type+", which is demanded by the calling code");
//				}
//			} else {
//				return res;
//			}
//		} else {
//			return Optional.empty();
//		}
	}
	
	// Guarantees it's there, will fail otherwise
	public String paramString(String key) {
		return (String)getCheckedValue(Optional.of(Property.STRING_PROPERTY), key, paramValue(key), Optional.empty());
	}

	public Optional<String> optionalString(String key) {
		return typeCheckedOperand(paramValue(key), key, Optional.of(Property.STRING_PROPERTY)).map(e->(String)e);

	}
	public String paramString(String key,Callable<String> defaultValue) {
		try {
			return typeCheckedOperand(paramValue(key), key,Optional.of(Property.STRING_PROPERTY)).map(e->(String)e).orElse(defaultValue.call()) ;
		} catch (Exception e) {
			throw new ReactiveParameterException("Default value failed",e);
		}
	}

	public Object paramObject(String key,Callable<Object> defaultValue) {
		try {
			return paramValue(key).orElse(defaultValue.call());
		} catch (Exception e) {
			throw new ReactiveParameterException("Default value failed",e);
		}
		
	}
	
	public int paramInteger(String key) {
		return (int)getCheckedValue(Optional.of(Property.INTEGER_PROPERTY), key, paramValue(key), Optional.empty());
	}

	public Optional<Integer> optionalInteger(String key) {
		return typeCheckedOperand(paramValue(key), key, Optional.of(Property.INTEGER_PROPERTY)).map(e->(Integer)e);
	}


	public int paramInteger(String key, Callable<Integer> defaultValue) {
		try {
			return typeCheckedOperand(paramValue(key), key,Optional.of(Property.INTEGER_PROPERTY)).map(e->(Integer)e).orElse(defaultValue.call()) ;
		} catch (Exception e) {
			throw new ReactiveParameterException("Default value failed",e);
		}
	}
	
	public boolean paramBoolean(String key) {
		return (boolean)getCheckedValue(Optional.of(Property.BOOLEAN_PROPERTY), key, paramValue(key), Optional.empty());
	}

	public Optional<Boolean> optionalBoolean(String key) {
		return typeCheckedOperand(paramValue(key), key, Optional.of(Property.BOOLEAN_PROPERTY)).map(e->(Boolean)e);
	}

	public boolean paramBoolean(String key, Callable<Boolean> defaultValue) {
		try {
			return typeCheckedOperand(paramValue(key), key,Optional.of(Property.BOOLEAN_PROPERTY)).map(e->(Boolean)e).orElse(defaultValue.call()) ;
		} catch (Exception e) {
			throw new ReactiveParameterException("Default value failed",e);
		}
	}
	
	private void resolveUnnamed() {
		
		List<? extends Object> resolved = unnamed.stream().map(e->{
			return e.apply(this.input.blockingGet(), this.currentMessage, Optional.of(this.paramMessage));
		}).collect(Collectors.toList());
		
		this.allResolved=true;
		this.resolvedUnnamed.addAll(resolved);
	}
	
	private void resolveNamed() {
		named.entrySet().forEach(e->{
			Optional<String> expectedType = expectedTypes.isPresent() ? Optional.ofNullable(expectedTypes.get().get(e.getKey())) : Optional.empty();
			resolveParam(e.getKey(),expectedType,e.getValue());
		});
		allResolved = true;
	}
	

	private Object resolveParam(String key,Optional<String> expectedType, ContextExpression function) {
		Object applied;
		try {
			applied = function.apply(input.blockingGet(), currentMessage,Optional.of(paramMessage));
			resolvedNamed.put(key, applied);
			
//			if(expectedType.isPresent() && !applied.type.equals(expectedType.get())) {
//				throw new ReactiveParameterException("Error evaluating key: "+key+" it is not of the expected type: "+expectedType.get()+" but of type: "+applied.type+" with value: "+applied.value+" path: "+sourcePath+" element: "+sourceElement+" -> "+ sourceElement.map(xml->""+xml.getStartLineNr()).orElse("<unknown>")+" message: "+currentMessage+" statemessage: "+paramMessage);
//			}
			return applied;
		} catch (Exception e1) {
			logger.error("Error applying param function for named param: "+key+" will put null.", e1);
			throw new ReactiveParameterException("Error applying param function for named param: "+key,e1);
		}
	}

}
