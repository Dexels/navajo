package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class ReduceToListTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public ReduceToListTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems,ReactiveParameters parameters) {
		return new ReduceToListTransformer(this,parameters);
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[] {"name","debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {"name"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String, String> r = new HashMap<>();
		r.put("debug", Property.BOOLEAN_PROPERTY);
		r.put("name", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}


	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE})) ;
	}

	@Override
	public Type outType() {
		return Type.MSGLIST;
	}
	

	@Override
	public String name() {
		return "reducetolist";
	}
}
