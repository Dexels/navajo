package com.dexels.navajo.reactive.stored;

import java.util.Arrays;
import java.util.Collections;
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
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.functions.Function;

public class InputStreamSourceFactory implements ReactiveSourceFactory, SourceMetadata {

	public InputStreamSourceFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveSource build(ReactiveParameters parameters) {
		return new InputStreamSource(this,parameters);
	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"path","classpath"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> types = new HashMap<>();
		types.put("path", Property.STRING_PROPERTY);
		types.put("classpath", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(types));
	}



}
