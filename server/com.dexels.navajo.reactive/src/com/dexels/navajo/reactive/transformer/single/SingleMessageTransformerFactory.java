package com.dexels.navajo.reactive.transformer.single;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SingleMessageTransformerFactory implements ReactiveTransformerFactory {

	public SingleMessageTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(XMLElement xml, Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMapper> mapperSupplier) {
		Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> joinermapper = ReactiveScriptParser.parseMapperList(xml.getChildren(), mapperSupplier);
		return new SingleMessageTransformer(joinermapper);
	}

}
