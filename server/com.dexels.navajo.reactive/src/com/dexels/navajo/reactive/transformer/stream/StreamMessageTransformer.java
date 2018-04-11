package com.dexels.navajo.reactive.transformer.stream;

import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
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
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	private final TransformerMetadata metadata;

	public StreamMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters, Optional<XMLElement> sourceElement, String sourcePath) {
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
		return flow->flow.map(di->di.message())
				.compose(StreamDocument.toMessageEvent(messageName,isArray))
				.map(DataItem::of);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
