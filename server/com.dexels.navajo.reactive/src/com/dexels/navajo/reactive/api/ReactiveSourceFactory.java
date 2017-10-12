package com.dexels.navajo.reactive.api;

import java.util.List;

public interface ReactiveSourceFactory {
	public ReactiveSource build(ReactiveParameters params, List<ReactiveTransformer> transformers);
}
