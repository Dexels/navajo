package com.dexels.navajo.reactive.source.sql;

import java.util.List;

import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

public class SQLReactiveSourceFactory implements ReactiveSourceFactory {

	public SQLReactiveSourceFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveSource build(ReactiveParameters parameters, List<ReactiveTransformer> transformers) {
		return new SQLReactiveSource(parameters, transformers);
	}

}
