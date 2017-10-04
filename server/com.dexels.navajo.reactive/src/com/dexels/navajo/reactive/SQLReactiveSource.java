package com.dexels.navajo.reactive;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class SQLReactiveSource implements ReactiveSource {

	private final List<Function<Optional<ReplicationMessage>, Object>> unnamedParameters;
//	private final String datasource;
//	private final String query;
	private final List<FlowableTransformer<ReplicationMessage, ReplicationMessage>> transformations;
	
	private final static Logger logger = LoggerFactory.getLogger(SQLReactiveSource.class);
	private final Map<String, Function<Optional<ReplicationMessage>, Object>> namedParameters;
	
	public SQLReactiveSource(Map<String, Function<Optional<ReplicationMessage>, Object>> namedParameters,
			List<Function<Optional<ReplicationMessage>, Object>> unnamedParameters, List<FlowableTransformer<ReplicationMessage, ReplicationMessage>> rest) {
		this.namedParameters = namedParameters;
		this.unnamedParameters = unnamedParameters;
		this.transformations = rest;
	}

	public Flowable<ReplicationMessage> execute(StreamScriptContext context,Optional<ReplicationMessage> current) {
		Flowable<ReplicationMessage> flow;
		try {
			flow = executeImmutable(context, current);
			for (FlowableTransformer<ReplicationMessage, ReplicationMessage> transformation : transformations) {
				try {
					flow = flow.compose(transformation);
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
		Object[] params = evaluateParams(context.getInput(), current);
		String datasource = (String) namedParameters.get("datasource").apply(current);
		String query = (String) namedParameters.get("query").apply(current);

		return SQL.query(datasource, context.tenant, query, params);
	}

	private Object[] evaluateParams(Optional<Navajo> in, Optional<ReplicationMessage> immutable) {
		final Navajo input = in.orElse(null);
		
		return unnamedParameters.stream().map(e->{
			try {
				return e.apply(immutable);
			} catch (Exception e1) {
				logger.error("Error: ", e1);
			}
			return null;

		}).toArray();
	}
}
