package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class ReduceTransformerFactory implements ReactiveTransformerFactory {

	public ReduceTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(String relativePath, XMLElement xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier, Function<String, ReactiveMapper> mapperSupplier) {

		Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> reducermapper = ReactiveScriptParser.parseReducerList(relativePath, xml.getChildren(), reducerSupplier);

		return new ReduceTransformer(reducermapper);
	}

}
