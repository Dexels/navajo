package com.dexels.navajo.reactive.transformer.call;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.functions.Function;

public class CallLocalSourceFactory implements ReactiveSourceFactory {

	public CallLocalSourceFactory() {
	}
	
	@Override
	public Type sourceType() {
		return Type.EVENT;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"service","debug","tenant"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"service"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("service", Property.STRING_PROPERTY);
		r.put("tenant", Property.STRING_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(r);	}

	@Override
	public ReactiveSource build(String relativePath, String type, List<ReactiveParseProblem> problems,
			Optional<XMLElement> x, ReactiveParameters params, List<ReactiveTransformer> transformers, Type finalType,
			Function<String, ReactiveMerger> reducerSupplier) {
		return new CallLocalSource(this, relativePath,type,problems,x,params,transformers,finalType,reducerSupplier);
	}

}
