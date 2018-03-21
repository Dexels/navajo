package com.dexels.navajo.reactive.source.input;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import com.dexels.navajo.reactive.api.SourceMetadata;
import com.dexels.navajo.reactive.source.single.SingleSource;

import io.reactivex.functions.Function;

public class InputSourceFactory implements ReactiveSourceFactory, SourceMetadata {

	@Override
	public ReactiveSource build(String relativePath, String type, List<ReactiveParseProblem> problems, Optional<XMLElement> x, ReactiveParameters params,
			List<ReactiveTransformer> transformers, Type finalType, Function<String, ReactiveMerger> reducerSupplier
			) {
//		return new InputSource(finalType);
		return new InputSource(this,params,transformers,finalType,x, relativePath);

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
		Map<String,String> res = new HashMap<>();
		res.put("name", "string");
		return Optional.of(Collections.unmodifiableMap(res));
	}


	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}
}
