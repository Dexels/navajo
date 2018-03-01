package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;

import io.reactivex.functions.Function;

public interface ReactiveSourceFactory {
	public ReactiveSource build(String relativePath, String type, Optional<XMLElement> x, ReactiveParameters params, List<ReactiveTransformer> transformers, 
			DataItem.Type finalType,Function<String, ReactiveMerger> reducerSupplier);
	public Type sourceType();

	default public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType) {
		return build("",type,Optional.empty(),params,transformers,finalType,ReactiveSource.emptyReducerSupplier);
	}

//	ReactiveSource rs = krsf.build("", "kafka",Optional.empty(), ReactiveParameters.empty(), Collections.emptyList(), DataItem.Type.MESSAGE, ReactiveSource.emptyReducerSupplier, ReactiveSource.emptyMapperSupplier);

}
