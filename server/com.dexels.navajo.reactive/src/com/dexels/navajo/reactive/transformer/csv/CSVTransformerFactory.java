package com.dexels.navajo.reactive.transformer.csv;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.Function;

public class CSVTransformerFactory implements ReactiveTransformerFactory {

	public CSVTransformerFactory() {
	}



	@Override
	public ReactiveTransformer build(String relativePath, XMLElement xml, Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier, Function<String, ReactiveMapper> mapperSupplier) {
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath,xml);
		return new CSVTransformer(parameters,xml, relativePath);
	}



}


//writeHeaders,columns,labels,delimiter