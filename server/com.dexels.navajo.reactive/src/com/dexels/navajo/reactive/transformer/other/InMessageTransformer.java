package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
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
	private final Optional<XMLElement> sourceElement;

	public InMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters,Optional<XMLElement> sourceElement) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.sourceElement = sourceElement;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, Optional.empty(), "");
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
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}
}
