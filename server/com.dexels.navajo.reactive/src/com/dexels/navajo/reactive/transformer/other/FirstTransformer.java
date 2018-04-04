package com.dexels.navajo.reactive.transformer.other;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class FirstTransformer implements ReactiveTransformer {

	private TransformerMetadata metadata;

	public FirstTransformer(TransformerMetadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return e->e.first(DataItem.of(ImmutableFactory.empty(),ImmutableFactory.empty())).toFlowable();
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
