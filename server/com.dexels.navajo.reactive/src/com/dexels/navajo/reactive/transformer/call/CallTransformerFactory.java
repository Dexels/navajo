package com.dexels.navajo.reactive.transformer.call;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.Function;

public class CallTransformerFactory implements ReactiveTransformerFactory, ParameterValidator {

	public CallTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(String relativePath,
			XMLElement xml, Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Function<String, ReactiveMapper> mapperSupplier) {
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return new CallTransformer(parameters,this,xml,relativePath);
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName","isArray","service","parallel","debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName","isArray","service"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("messageName", Property.STRING_PROPERTY);
		r.put("service", Property.STRING_PROPERTY);
		r.put("parallel", Property.INTEGER_PROPERTY);
		r.put("isArray", Property.BOOLEAN_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(r);
	}

}
