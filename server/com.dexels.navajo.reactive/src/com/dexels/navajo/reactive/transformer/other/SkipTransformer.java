package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class SkipTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private TransformerMetadata metadata;

	public SkipTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.metadata = metadata;
		this.parameters = parameters;

	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, Optional.empty(), "");
		int count = parms.paramInteger("count");
		return e->e.skip(count);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
