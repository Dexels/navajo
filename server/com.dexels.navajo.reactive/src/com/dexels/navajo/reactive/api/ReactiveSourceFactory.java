package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.ReactiveParseProblem;

import io.reactivex.functions.Function;

public interface ReactiveSourceFactory extends SourceMetadata {
	public ReactiveSource build(String relativePath, String type, List<ReactiveParseProblem> problems, Optional<XMLElement> x, ReactiveParameters params, List<ReactiveTransformer> transformers, 
			DataItem.Type finalType,Function<String, ReactiveMerger> reducerSupplier);

	default public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveParseProblem> problems, List<ReactiveTransformer> transformers, DataItem.Type finalType) {
		return build("",type,problems, Optional.empty(),params,transformers,finalType,ReactiveSource.emptyReducerSupplier);
	}

//	ReactiveSource rs = krsf.build("", "kafka",Optional.empty(), ReactiveParameters.empty(), Collections.emptyList(), DataItem.Type.MESSAGE, ReactiveSource.emptyReducerSupplier, ReactiveSource.emptyMapperSupplier);

}
