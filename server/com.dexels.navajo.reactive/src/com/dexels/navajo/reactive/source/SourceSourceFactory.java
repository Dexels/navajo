package com.dexels.navajo.reactive.source;

import java.util.List;

import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

public class SourceSourceFactory implements ReactiveSourceFactory {

	@Override
	public ReactiveSource build(String type, ReactiveParameters params, List<ReactiveTransformer> transformers) {
		return new SourceSource();
	}
}
