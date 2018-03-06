package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.transformer.call.CallTransformer;

import io.reactivex.functions.Function;

public class StoreFactory implements ReactiveTransformerFactory, ParameterValidator {

	public StoreFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveTransformer build(String relativePath, Optional<XMLElement> xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier) {
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return null; //new Store();
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","value"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","value"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> result = new HashMap<String, String>();
		result.put("name", Property.STRING_PROPERTY);
		result.put("value", Property.INTEGER_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(result));
	}

}
