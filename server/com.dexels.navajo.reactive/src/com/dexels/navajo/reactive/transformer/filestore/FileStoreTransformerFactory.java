package com.dexels.navajo.reactive.transformer.filestore;

import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

public class FileStoreTransformerFactory implements ReactiveTransformerFactory {

	public FileStoreTransformerFactory() {} 
	
	@Override
	public ReactiveTransformer build(ReactiveParameters parameters) {
		return new FileStoreTransformer(parameters);
	}
	

}
