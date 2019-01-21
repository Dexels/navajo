package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class ScanTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public ScanTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems,ReactiveParameters parameters) {


		return new ScanTransformer(this,parameters);
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
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE})) ;
	}

	@Override
	public Type outType() {
		return Type.SINGLEMESSAGE;
	}
	

	@Override
	public String name() {
		return "scan";
	}
}
