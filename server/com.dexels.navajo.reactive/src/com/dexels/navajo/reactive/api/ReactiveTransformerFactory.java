package com.dexels.navajo.reactive.api;

import com.dexels.navajo.document.nanoimpl.XMLElement;

import io.reactivex.functions.Function;

public interface ReactiveTransformerFactory {
	public ReactiveTransformer build(
			String relativePath,
			XMLElement xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Function<String, ReactiveMapper> mapperSupplier
			);
}
