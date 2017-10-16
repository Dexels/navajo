package com.dexels.navajo.reactive.source.sql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;

public class SQLReactiveSource implements ReactiveSource {
	
	private final ReactiveParameters parameters;
	private final List<ReactiveTransformer> transformers;
	
	public SQLReactiveSource(ReactiveParameters params, List<ReactiveTransformer> transformers) {
		this.parameters = params;
		this.transformers = transformers;
	}

	public Flowable<DataItem> execute(StreamScriptContext context,Optional<ReplicationMessage> current) {
		return executeImmutable(context, current);
	}
	
	public Flowable<DataItem> executeImmutable(StreamScriptContext context,Optional<ReplicationMessage> current)  {
		Object[] params = evaluateParams(context, current);
		Map<String,Operand> paramMap = parameters.resolveNamed(context, current);
		String datasource = (String) paramMap.get("resource").value;
		String query = (String) paramMap.get("query").value;
		Flowable<DataItem> flow = SQL.query(datasource, context.tenant, query, params).map(d->DataItem.of(d));
		for (ReactiveTransformer trans : transformers) {
			flow = flow.compose(trans.execute(context));
		}
		return flow;
	}

	private Object[] evaluateParams(StreamScriptContext context, Optional<ReplicationMessage> immutable) {
		return parameters.resolveUnnamed(context, immutable).stream().map(e->e.value).toArray();
	}

}
