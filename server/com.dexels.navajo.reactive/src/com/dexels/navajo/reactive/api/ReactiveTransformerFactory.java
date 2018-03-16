package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;

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
			Function<String, ReactiveMerger> reducerSupplier
			);

	default 	public ReactiveTransformer build(List<ReactiveParseProblem> problems, ReactiveParameters parameters) {
		return build("",problems,parameters, Optional.empty(),n->null,n->null,n->null);
	}

}
