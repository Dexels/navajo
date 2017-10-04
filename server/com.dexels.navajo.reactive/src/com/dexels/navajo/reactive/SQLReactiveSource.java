package com.dexels.navajo.reactive;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class SQLReactiveSource implements ReactiveSource {

	private final List<String> unnamedParameters;
	private final String datasource;
	private final String query;
	private final List<FlowableTransformer<ReplicationMessage, ReplicationMessage>> transformations;
	
	private final static Logger logger = LoggerFactory.getLogger(SQLReactiveSource.class);

	
	public SQLReactiveSource(Map<String, String> namedParameters,
			List<String> unnamedParameters, List<FlowableTransformer<ReplicationMessage, ReplicationMessage>> rest) {
		datasource = namedParameters.get("datasource");
		query = namedParameters.get("query");
		this.unnamedParameters = unnamedParameters;
		this.transformations = rest;
	}

	public Flowable<ReplicationMessage> execute(StreamScriptContext context,Optional<ReplicationMessage> current) {
		Flowable<ReplicationMessage> flow = executeImmutable(context, current);
		for (FlowableTransformer<ReplicationMessage, ReplicationMessage> transformation : transformations) {
			try {
				flow = flow.compose(transformation);
			} catch (Exception e) {
				return Flowable.error(e);
			}
		}
		return flow;
	}
	
	public Flowable<ReplicationMessage> executeImmutable(StreamScriptContext context,Optional<ReplicationMessage> current) {
		Object[] params = evaluateParams(context.getInput(), current);
		return SQL.query(datasource, context.tenant, query, params);
	}

	private Object[] evaluateParams(Optional<Navajo> in, Optional<ReplicationMessage> immutable) {
		final Navajo input = in.orElse(null);
		return unnamedParameters.stream().map(e->{
			try {
				return Expression.evaluate(e, input, null, null,null,null,null,null,immutable).value;
			} catch (TMLExpressionException|SystemException e1) {
				logger.error("Evaluation failed for expression: "+e);
				if(immutable.isPresent()) {
					ReplicationFactory.getInstance().describe(immutable.get());
				} else {
					logger.warn("No immutable message");
				}
				return null;
			}
		}).toArray();
	}
}
