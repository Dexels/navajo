package com.dexels.navajo.reactive.sql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SQLReactiveSource implements ReactiveSource {

	private final List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Operand>> unnamedParameters;
//	private final String datasource;
//	private final String query;
	private final List<Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>>> transformations;
	
	private final static Logger logger = LoggerFactory.getLogger(SQLReactiveSource.class);
	private final Map<String, BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Operand>> namedParameters;
	private String outputName;
	private boolean isOutputArray;
	
	public SQLReactiveSource(Map<String, BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Operand>> namedParameters
			, List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Operand>> unnamedParameters
			, List<Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>>> rest
			, String outputName, boolean isOutputArray) {
		this.namedParameters = namedParameters;
		this.unnamedParameters = unnamedParameters;
		this.transformations = rest;
		this.outputName = outputName;
		this.isOutputArray = isOutputArray;
	}

	public Flowable<ReplicationMessage> execute(StreamScriptContext context,Optional<ReplicationMessage> current) {
		Flowable<ReplicationMessage> flow;
		try {
			flow = executeImmutable(context, current);
			for (Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>> transformation : transformations) {
				try {
					flow = flow.compose(transformation.apply(context));
				} catch (Exception e) {
					return Flowable.error(e);
				}
			}
			return flow;
		} catch (Exception e1) {
			return Flowable.error(e1);
		}
	}
	
	public Flowable<ReplicationMessage> executeImmutable(StreamScriptContext context,Optional<ReplicationMessage> current) throws Exception {
		Object[] params = evaluateParams(context, current);
		String datasource = (String) namedParameters.get("resource").apply(context,current).value;
		String query = (String) namedParameters.get("query").apply(context,current).value;

		return SQL.query(datasource, context.tenant, query, params);
	}

	private Object[] evaluateParams(StreamScriptContext context, Optional<ReplicationMessage> immutable) {
		final Navajo input = context.getInput().orElse(null);
		
		return unnamedParameters.stream().map(e->{
			try {
				return e.apply(context,immutable).value;
			} catch (Exception e1) {
				logger.error("Error: ", e1);
			}
			return null;

		}).toArray();
	}

	@Override
	public String getOutputName() {
		return outputName;
	}

	@Override
	public boolean isOutputArray() {
		return isOutputArray;
	}
}
