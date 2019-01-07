package com.dexels.navajo.reactive.transformer.stream;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class StreamMessageTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public StreamMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = parameters.resolve(context, Optional.empty(), ImmutableFactory.empty(), metadata);
		String messageName = resolved.paramString("messageName");
		boolean isArray = resolved.paramBoolean("isArray");
		// TODO remove duplication
		return flow->flow.map(di->di.message())
				.doOnNext(e->System.err.println(">>msg>> "+e))
				.compose(StreamDocument.toMessageEvent(messageName,isArray))
				.doOnNext(e->System.err.println(">ble> "+e))
//				.doOnNext(e->System.err.println(">>>>>>>>>> propagating evnt: "+e))
				.map(DataItem::of);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
