package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class ToSubMessage implements ReactiveMerger, ParameterValidator, ReactiveTransformer {

	private final ReactiveParameters parameters;

	public ToSubMessage(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		return context->(item)->{
			ReactiveResolvedParameters resolved = params.resolveNamed(context, Optional.of(item.message()),item.stateMessage(), this, xml, relativePath);
			;
			return DataItem.of(item.message().withSubMessage(resolved.paramString("name"), item.stateMessage().orElse(ImmutableFactory.empty())), item.stateMessage());
		};
	
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"name"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"name"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("name", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->{
			try {
				return flow.map(this.execute(parameters).apply(context));
			} catch (Exception e) {
				return Flowable.error(e);
			}
		};
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.SINGLEMESSAGE}));
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}
}
