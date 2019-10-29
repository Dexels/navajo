package com.dexels.navajo.reactive.transformer.parseevents;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class ParseEventStream implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private final TransformerMetadata metadata;
//	private boolean isSingle;

	public ParseEventStream(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
//		this.isSingle = isSingle;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = parameters.resolve(context, current, param, metadata);
		Optional<String> path = resolved.optionalString("path").map(pth->pth.startsWith("/") ? pth.substring(1) : pth);
			return flow->flow.map(di->di.eventStream())
					.concatMap(e->e)
					.compose(StreamDocument.eventsToImmutable(path))
					.map(DataItem::of);

	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public ReactiveParameters parameters() {
		return parameters;
	}

}
