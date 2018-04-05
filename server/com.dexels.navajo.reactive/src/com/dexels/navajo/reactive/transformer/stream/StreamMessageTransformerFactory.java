package com.dexels.navajo.reactive.transformer.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.functions.Function;

public class StreamMessageTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public StreamMessageTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(String relativePath, List<ReactiveParseProblem> problems, ReactiveParameters parameters, 
			Optional<XMLElement> xml, Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput) {
		return new StreamMessageTransformer(this,parameters,xml,relativePath);
	}


	@Override
	public Set<Type> inType() {
		return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new Type[]{Type.SINGLEMESSAGE,Type.MESSAGE}))); // Type.SINGLEMESSAGE;
	}

	@Override
	public Type outType() {
		return Type.EVENT;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName","isArray"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("messageName", Property.STRING_PROPERTY);
		r.put("isArray", Property.BOOLEAN_PROPERTY);
		return Optional.of(r);
	}

}
