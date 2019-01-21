package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;

public interface ReactiveTransformerFactory extends TransformerMetadata {
	public ReactiveTransformer build(
			List<ReactiveParseProblem> problems,
			ReactiveParameters parameters
			);

}
