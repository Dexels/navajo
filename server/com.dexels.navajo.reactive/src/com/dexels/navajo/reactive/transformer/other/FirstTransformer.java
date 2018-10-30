package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class FirstTransformer implements ReactiveTransformer {

	private final TransformerMetadata metadata;
	private final Optional<XMLElement> sourceElement;

	public FirstTransformer(TransformerMetadata metadata,Optional<XMLElement> sourceElement) {
		this.metadata = metadata;
		this.sourceElement = sourceElement;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		return e->e.first(DataItem.of(ImmutableFactory.empty(),ImmutableFactory.empty())).toFlowable();
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
