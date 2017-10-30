package com.dexels.navajo.reactive.source;

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

public class SourceSourceFactory implements ReactiveSourceFactory {

	@Override
	public ReactiveSource build(String type, XMLElement x, ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType,Function<String, ReactiveMapper> mapperSupplier) {
		return new SourceSource(finalType);
	}
	
	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}
}
