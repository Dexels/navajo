package com.dexels.navajo.reactive.source.single;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
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
	public ReactiveSource build(String type, XMLElement x, ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType,Function<String, ReactiveMapper> mapperSupplier) {
		XMLElement mapElement = x.getChildByTagName("map");
		Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> mapMapper = mapElement==null? Optional.empty() : Optional.of(ReactiveScriptParser.parseMapperList(mapElement.getChildren(), mapperSupplier));

		return new SingleSource(params,transformers,finalType,mapMapper);
	}

	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}
}
