package com.dexels.navajo.reactive.source.single;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SingleSourceFactory implements ReactiveSourceFactory {

	public SingleSourceFactory() {
	}

	@Override
	public ReactiveSource build(String relativePath, String type, XMLElement x, ReactiveParameters params,
			List<ReactiveTransformer> transformers, Type finalType, Function<String, ReactiveMerger> reducerSupplier,
			Function<String, ReactiveMapper> mapperSupplier
			) {
		XMLElement mapElement = x.getChildByTagName("map");

		Optional<Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>>> reduceMapper = 
				mapElement==null? Optional.empty() : Optional.of(ReactiveScriptParser.parseReducerList(relativePath, mapElement.getChildren(), reducerSupplier));

		
		return new SingleSource(params,transformers,finalType,reduceMapper,x, relativePath);
	}

	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}



}
