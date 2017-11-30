package com.dexels.navajo.reactive.stored;

import java.util.List;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.functions.Function;

public class InputStreamSourceFactory implements ReactiveSourceFactory {

	public InputStreamSourceFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveSource build(String relativePath, String type, XMLElement x, ReactiveParameters params,
			List<ReactiveTransformer> transformers, Type finalType, Function<String, ReactiveMerger> reducerSupplier,
			Function<String, ReactiveMapper> mapperSupplier) {
		return new InputStreamSource(params,relativePath,x,finalType,transformers);
	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}

}
