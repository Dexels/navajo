package com.dexels.navajo.reactive.source.test;

import java.util.Arrays;
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

import io.reactivex.functions.Function;

public class EventStreamSourceFactory implements ReactiveSourceFactory {

	public EventStreamSourceFactory() {
	}

	@Override
	public Type sourceType() {
		return Type.EVENTSTREAM;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"classpath"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> result = new HashMap<>();
		result.put("classpath", "string");
		return Optional.ofNullable(result);
	}

	@Override
	public ReactiveSource build(ReactiveParameters parameters) {
//		problems.add(ReactiveParseProblem.of("event source is missing a source ('classpath' only supported now)"));

		return new EventStreamSource(this,parameters);
	}

}
