package com.dexels.navajo.reactive.source.ndjson;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.functions.Function;

public class NDJsonSourceFactory implements ReactiveSourceFactory {

	@Override
	public Type sourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReactiveSource build(String relativePath, String type, List<ReactiveParseProblem> problems,
			Optional<XMLElement> x, ReactiveParameters params, List<ReactiveTransformer> transformers, Type finalType,
			Function<String, ReactiveMerger> reducerSupplier) {
		// TODO Auto-generated method stub
		return null;
	}

}