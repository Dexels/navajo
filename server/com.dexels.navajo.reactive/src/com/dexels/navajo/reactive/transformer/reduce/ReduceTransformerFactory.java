package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.functions.Function;

public class ReduceTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public ReduceTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(String relativePath, List<ReactiveParseProblem> problems,ReactiveParameters parameters, Optional<XMLElement> xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput) {

		Function<StreamScriptContext,Function<DataItem,DataItem>> reducermapper = ReactiveScriptParser.parseReducerList(relativePath, problems,xml.map(e->(List<XMLElement>)e.getChildren()) , reducerSupplier,useGlobalInput);
		return new ReduceTransformer(this, reducermapper,parameters);
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
}
