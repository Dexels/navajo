package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;

public interface ReactiveTransformerFactory extends TransformerMetadata {
	public ReactiveTransformer build(
			Type parentType,
			List<ReactiveParseProblem> problems,
			ReactiveParameters parameters,
			ReactiveBuildContext buildContext
			);

	default 	public ReactiveTransformer build(List<ReactiveParseProblem> problems, ReactiveParameters parameters) {
		ReactiveBuildContext buildContext = ReactiveBuildContext.of(n->null,(n,type)->null,n->null,Collections.emptySet(),Collections.emptySet(),true);
		return build(Type.ANY, problems,parameters,buildContext);
	}

}
