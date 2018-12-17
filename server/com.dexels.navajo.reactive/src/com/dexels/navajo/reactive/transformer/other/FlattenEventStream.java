package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class FlattenEventStream implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public FlattenEventStream(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current,param, metadata);
		int parallel = parms.optionalInteger("parallel").orElse(1);
		if (parallel < 2) {
			return flow->flow.concatMap(e->e.eventStream()).map(DataItem::of)
					.doOnNext(e->System.err.println("Item detected: "+e));
		} else {
			return flow->flow.concatMapEager(e->e.eventStream()).map(DataItem::of);
		}
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
