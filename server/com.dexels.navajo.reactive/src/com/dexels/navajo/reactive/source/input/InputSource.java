package com.dexels.navajo.reactive.source.input;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class InputSource implements ReactiveSource {
	
	private final ReactiveParameters params;
//	private final Optional<XMLElement> sourceElement;
//	private final String sourcePath;
	private final SourceMetadata metadata;
	
	public InputSource(SourceMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.params = params;
//		this.sourceElement = sourceElement;
//		this.sourcePath = sourcePath;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = params.resolve(context, current, param, metadata);
		Optional<String> path = resolved.optionalString("path");
		return context.inputFlowable().orElse(Flowable.empty()); // .orElseGet(()->context.inputFlowable().orElse(context.getInput().toFlowable().flatMap(e->NavajoDomStreamer.feedFlowable(e))))
//				.compose(StreamDocument.eventsToImmutable(path))
//				.map(DataItem::of);
	}


	@Override
	public boolean streamInput() {
		return true;
	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}

}
