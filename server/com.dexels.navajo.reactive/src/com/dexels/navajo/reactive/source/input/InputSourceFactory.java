package com.dexels.navajo.reactive.source.input;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.SourceMetadata;

public class InputSourceFactory implements ReactiveSourceFactory, SourceMetadata {

	@Override
	public ReactiveSource build(ReactiveParameters parameters) {
		return new  InputSource(this, parameters);
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
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

