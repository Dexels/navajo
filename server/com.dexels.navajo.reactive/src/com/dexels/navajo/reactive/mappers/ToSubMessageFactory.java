package com.dexels.navajo.reactive.mappers;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.Function;

public class ToSubMessageFactory implements ReactiveTransformerFactory {

	public ToSubMessageFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveTransformer build(String relativePath, Optional<XMLElement> xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier) {
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return new ToSubMessage(parameters);
	}

}
