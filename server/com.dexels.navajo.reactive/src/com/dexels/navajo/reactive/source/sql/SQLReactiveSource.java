package com.dexels.navajo.reactive.source.sql;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;

public class SQLReactiveSource implements ReactiveSource, ParameterValidator {
	
	private final ReactiveParameters parameters;
	private final List<ReactiveTransformer> transformers;
	private final Type finalType;
	private final Optional<XMLElement> sourceElement;
	private final String sourcePath;
	
	public SQLReactiveSource(ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType, Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = params;
		this.transformers = transformers;
		this.finalType = finalType;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context,Optional<ImmutableMessage> current) {
		Object[] unnamedParams = evaluateParams(context, current);
		ReactiveResolvedParameters params = parameters.resolveNamed(context, current, ImmutableFactory.empty(), this, sourceElement, sourcePath);
		String datasource = params.paramString("resource");
		String query = params.paramString("query");
		Flowable<DataItem> flow = SQL.query(datasource, context.tenant, query, unnamedParams).map(d->DataItem.of(d));
		for (ReactiveTransformer trans : transformers) {
			flow = flow.compose(trans.execute(context));
		}
		return flow;
	}

	private Object[] evaluateParams(StreamScriptContext context, Optional<ImmutableMessage> immutable) {
		return parameters.resolveUnnamed(context, immutable, ImmutableFactory.empty()).stream().map(e->e.value).toArray();
	}

	@Override
	public Type dataType() {
		return Type.MESSAGE;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"resource","query"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"resource","query"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("resource", Property.STRING_PROPERTY);
		r.put("query", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

}
