package com.dexels.navajo.reactive.transformer.eventstream;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class EventStreamMessageTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public EventStreamMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = parameters.resolve(context,current,param, metadata);
		String messageName = resolved.paramString("messageName");
		boolean isArray = resolved.paramBoolean("isArray");
		return flow->{
			Flowable<Flowable<NavajoStreamEvent>> transform = flow.map(di->di.message())
					.map(msg->StreamDocument.toMessageEventStream(messageName,msg,isArray));
			return transform.map(DataItem::ofEventStream);
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
