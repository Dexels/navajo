package com.dexels.navajo.reactive.transformer.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;

public class FirstTransformer implements ReactiveTransformer, ParameterValidator {

	private final ReactiveParameters parameters;

	public FirstTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), this, Optional.empty(), "");
		return e->e.first(DataItem.of(ImmutableFactory.empty(),ImmutableFactory.empty())).toFlowable();
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.MESSAGE}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.SINGLEMESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		return Optional.of(Collections.emptyMap());
	}

}
