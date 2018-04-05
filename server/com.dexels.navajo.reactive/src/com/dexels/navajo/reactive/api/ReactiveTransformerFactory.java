package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.ReactiveParseProblem;

import io.reactivex.functions.Function;

public interface ReactiveTransformerFactory extends TransformerMetadata {
	public ReactiveTransformer build(
			String relativePath,
			List<ReactiveParseProblem> problems,
			ReactiveParameters parameters,
			Optional<XMLElement> xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput
			);

	default 	public ReactiveTransformer build(List<ReactiveParseProblem> problems, ReactiveParameters parameters) {
		return build("",problems,parameters, Optional.empty(),n->null,n->null,n->null,Collections.emptySet(),Collections.emptySet(),true);
	}

}
