package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
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
	private final Optional<XMLElement> sourceElement;

	public FlattenEventStream(TransformerMetadata metadata, ReactiveParameters parameters,Optional<XMLElement> sourceElement) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.sourceElement = sourceElement;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, Optional.empty(), "");
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

	@Override
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}
}
