package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public interface ReactiveSourceFactory {
	public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveTransformer> transformers, Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> function);
}
