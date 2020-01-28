package com.dexels.navajo.reactive.source.topology;

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
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class FilterTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public FilterTransformerFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems, 
			ReactiveParameters parameters) {

		return new FilterTransformer(this,parameters);
	}


	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.MESSAGE,DataItem.Type.SINGLEMESSAGE}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList());
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList());
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String, String> r = new HashMap<>();
		r.put("filter", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

	@Override
	public String name() {
		return "filter";
	}
}
