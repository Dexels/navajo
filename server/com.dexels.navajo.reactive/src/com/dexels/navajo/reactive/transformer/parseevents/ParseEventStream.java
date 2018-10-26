package com.dexels.navajo.reactive.transformer.parseevents;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class ParseEventStream implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	private final TransformerMetadata metadata;
	private final Type inferredType;
//	private boolean isSingle;

	public ParseEventStream(TransformerMetadata metadata, ReactiveParameters parameters, Optional<XMLElement> sourceElement, String sourcePath, Type inferredType) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.metadata = metadata;
		this.inferredType = inferredType;
//		this.isSingle = isSingle;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata,sourceElement,sourcePath);
		Optional<String> path = resolved.optionalString("path");
		switch(inferredType) {
		case EVENT:
			return flow->flow.map(di->di.event())
					.compose(StreamDocument.eventsToImmutable(path))
//					.doOnNext(e->System.err.println("Element encountered: "+e))
					.map(DataItem::of);
		case EVENTSTREAM:
			return flow->flow.map(di->di.eventStream())
					.concatMap(e->e)
					.compose(StreamDocument.eventsToImmutable(path))
//					.doOnNext(e->System.err.println("Element encountered: "+e))
					.map(DataItem::of);
		default:
			throw new RuntimeException("Unexpected type: "+inferredType+" in "+metadata.name());
			
		}
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public Optional<XMLElement> sourceElement() {
		return this.sourceElement;
	}

}
