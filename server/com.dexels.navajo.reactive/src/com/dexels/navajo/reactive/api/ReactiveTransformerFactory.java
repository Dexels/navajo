package com.dexels.navajo.reactive.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.ReactiveParseProblem;

import io.reactivex.functions.Function;

public interface ReactiveTransformerFactory {
	public ReactiveTransformer build(
			String relativePath,
			List<ReactiveParseProblem> problems,
			Optional<XMLElement> xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier
			);

	default 	public ReactiveTransformer build(List<ReactiveParseProblem> problems) {
		return build("",problems,Optional.empty(),n->null,n->null,n->null);
	}

}
