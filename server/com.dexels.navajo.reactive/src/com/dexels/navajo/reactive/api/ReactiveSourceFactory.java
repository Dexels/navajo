package com.dexels.navajo.reactive.api;

import java.util.List;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;

import io.reactivex.functions.Function;

public interface ReactiveSourceFactory {
	public ReactiveSource build(String relativePath, String type, XMLElement x, ReactiveParameters params, List<ReactiveTransformer> transformers, 
			DataItem.Type finalType,Function<String, ReactiveMerger> reducerSupplier,Function<String, ReactiveMapper> mapperSupplier);
	public Type sourceType();
}
