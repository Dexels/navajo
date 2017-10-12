package com.dexels.navajo.reactive.transformer.csv;

import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

public class CSVTransformerFactory implements ReactiveTransformerFactory {

	public CSVTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(
			ReactiveParameters parameters) {
		return new CSVTransformer(parameters);
	}

}
