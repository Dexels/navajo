package com.dexels.navajo.reactive.source.single;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
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
	public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveTransformer> transformers, Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> datamapper, DataItem.Type finalType) {
		return new SingleSource(params,transformers,datamapper,finalType);
	}

	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}
}
