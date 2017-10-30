package com.dexels.navajo.reactive.source.sql;

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

public class SQLReactiveSourceFactory implements ReactiveSourceFactory {

	public SQLReactiveSourceFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveSource build(String type, ReactiveParameters parameters, List<ReactiveTransformer> transformers, Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> dataMapper, DataItem.Type finalType) {
		return new SQLReactiveSource(parameters, transformers, dataMapper,finalType);
	}

	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}
}
