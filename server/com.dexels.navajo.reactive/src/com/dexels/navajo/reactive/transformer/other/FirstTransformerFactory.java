package com.dexels.navajo.reactive.transformer.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class FirstTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public FirstTransformerFactory() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ReactiveTransformer build(Type parentType, List<ReactiveParseProblem> problems,
			ReactiveParameters parameters, ReactiveBuildContext buildContext) {
		return new FirstTransformer(this);
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

	@Override
	public String name() {
		return "first";
	}


}
