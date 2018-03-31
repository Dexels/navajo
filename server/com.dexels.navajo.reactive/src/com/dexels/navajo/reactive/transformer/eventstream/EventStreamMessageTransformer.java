package com.dexels.navajo.reactive.transformer.eventstream;

import java.util.Optional;

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
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	private final TransformerMetadata metadata;

	public EventStreamMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters, Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata,sourceElement,sourcePath);
		String messageName = resolved.paramString("messageName");
		boolean isArray = resolved.paramBoolean("isArray");
		// TODO remove duplication
		return flow->{
			Flowable<Flowable<NavajoStreamEvent>> transform = flow.map(di->di.message())
					.map(msg->StreamDocument.toMessageEventStream(messageName,msg,isArray));
					
//			if (isArray) {
//				transform = transform.
//			return flow->
//				flow.map(di->di.message()).concatMap(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray)).compose(StreamDocument.inArray(messageName)).map(DataItem::of);
//		} else {
//			return flow->flow.take(1).
//					map(di->di.message()).concatMap(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray)).map(DataItem::of);
//		}
			
			return transform.map(DataItem::ofEventStream);
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
