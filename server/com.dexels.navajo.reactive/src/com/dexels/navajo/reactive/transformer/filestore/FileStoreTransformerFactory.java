package com.dexels.navajo.reactive.transformer.filestore;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.Function;

public class FileStoreTransformerFactory implements ReactiveTransformerFactory {

	public FileStoreTransformerFactory() {} 
	
	@Override
	public ReactiveTransformer build(XMLElement xml,Function<String,ReactiveSourceFactory> sourceSupplier, Function<String,ReactiveTransformerFactory> factorySupplier, Function<String, ReactiveMapper> mapperSupplier) {
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(xml);
		return new FileStoreTransformer(parameters);
	}
	

}