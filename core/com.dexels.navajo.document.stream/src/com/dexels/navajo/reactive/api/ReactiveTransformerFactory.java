package com.dexels.navajo.reactive.api;

import java.util.List;

import com.dexels.navajo.document.stream.ReactiveParseProblem;

public interface ReactiveTransformerFactory extends TransformerMetadata {
	public ReactiveTransformer build(
			List<ReactiveParseProblem> problems,
			ReactiveParameters parameters
			);

}
