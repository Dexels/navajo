package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.ReactiveBuildContext;

public interface ReactiveTransformerFactory extends TransformerMetadata {
	public ReactiveTransformer build(
			String relativePath,
			List<ReactiveParseProblem> problems,
			ReactiveParameters parameters,
			Optional<XMLElement> xml,
			ReactiveBuildContext buildContext
			);

	default 	public ReactiveTransformer build(List<ReactiveParseProblem> problems, ReactiveParameters parameters) {
		ReactiveBuildContext buildContext = ReactiveBuildContext.of(n->null,n->null,n->null,Collections.emptySet(),Collections.emptySet(),true);
		return build("",problems,parameters, Optional.empty(),buildContext);
	}

}
