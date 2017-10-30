package com.dexels.navajo.reactive.source;

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

public class SourceSourceFactory implements ReactiveSourceFactory {

	@Override
	public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveTransformer> transformers, Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> function, DataItem.Type finalType) {
		return new SourceSource(finalType);
	}
	
	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}
}
