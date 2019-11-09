package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class InMessageTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public InMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current,param, metadata);
		boolean isArray = parms.paramBoolean("isArrayElement");
		String name = parms.paramString("name");
		if(isArray) {
			return e->e.map(f->f.eventStream()
					.filter(event->!event.type().equals(NavajoEventTypes.NAVAJO_DONE) && !event.type().equals(NavajoEventTypes.NAVAJO_STARTED))
					.compose(StreamDocument.inArrayElement(name))
					.compose(StreamDocument.inArray(name))
					)
					.map(g->DataItem.ofEventStream(g));
		} else {
			return e->e.map(f->f.eventStream()
					.compose(StreamDocument.inMessage(name))
					)
					.map(g->DataItem.ofEventStream(g));
		}
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
