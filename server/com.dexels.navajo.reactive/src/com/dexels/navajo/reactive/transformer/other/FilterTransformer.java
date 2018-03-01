package com.dexels.navajo.reactive.transformer.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;

public class FilterTransformer implements ReactiveTransformer, ParameterValidator {

	private final ReactiveParameters parameters;

	public FilterTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return e->e.filter(item->{
			ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.of(item.message()), Optional.empty(), this, Optional.empty(), "");
			return parms.paramBoolean("filter");
		});
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.MESSAGE}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[] {"filter"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {"filter"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String, String> r = new HashMap<>();
		r.put("filter", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

}
