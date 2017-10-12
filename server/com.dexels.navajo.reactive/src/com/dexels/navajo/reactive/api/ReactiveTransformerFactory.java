package com.dexels.navajo.reactive.api;

import com.dexels.navajo.document.nanoimpl.XMLElement;

import io.reactivex.functions.Function;

public interface ReactiveTransformerFactory {
	public ReactiveTransformer build(XMLElement xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMapper> mapperSupplier);
}
