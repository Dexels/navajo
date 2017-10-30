package com.dexels.navajo.reactive.source.sql;

import java.util.List;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.functions.Function;

public class SQLReactiveSourceFactory implements ReactiveSourceFactory {

	public SQLReactiveSourceFactory() {
	}

	@Override
	public ReactiveSource build(String type, XMLElement x, ReactiveParameters parameters, List<ReactiveTransformer> transformers, DataItem.Type finalType,Function<String, ReactiveMapper> mapperSupplier) {
		return new SQLReactiveSource(parameters, transformers,finalType);
	}

	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}
}
