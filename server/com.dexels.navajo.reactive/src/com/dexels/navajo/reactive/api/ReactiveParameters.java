package com.dexels.navajo.reactive.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.functions.BiFunction;

public class ReactiveParameters {
	
	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveParameters.class);


	public final Map<String,BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> named;
	public final List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> unnamed;
	private ReactiveParameters(Map<String,BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> namedParameters,List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> unnamedParameters) {
		this.named = namedParameters;
		this.unnamed = unnamedParameters;
	}

	public List<Operand> resolveUnnamed(StreamScriptContext context ,Optional<ReplicationMessage> currentMessage) {
		return unnamed.stream().map(e->{
			try {
				return e.apply(context, currentMessage);
			} catch (Exception e1) {
				logger.error("Error applying param function: ", e1);
				return new Operand(null,"string",null);
			}
		}).collect(Collectors.toList());
	}
	
	public Map<String,Operand> resolveNamed(StreamScriptContext context ,Optional<ReplicationMessage> currentMessage) {
		Map<String,Operand> result = new HashMap<>();
		named.entrySet().forEach(e->{
			Operand applied;
			try {
				applied = e.getValue().apply(context, currentMessage);
				result.put(e.getKey(), applied);
			} catch (Exception e1) {
				logger.error("Error applying param function for named param: "+e.getKey()+" will put null.", e1);
				result.put(e.getKey(), null);
			}
		});
		return result;
	}
	public static ReactiveParameters of(Map<String,BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> namedParameters,List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> unnamedParameters) {
		return new ReactiveParameters(namedParameters, unnamedParameters);
	}

	
}
