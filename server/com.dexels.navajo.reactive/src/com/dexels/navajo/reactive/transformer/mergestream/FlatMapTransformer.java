package com.dexels.navajo.reactive.transformer.mergestream;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class FlatMapTransformer implements ReactiveTransformer {

	private final ReactiveSource source;
	private final TransformerMetadata metadata;
	private final Optional<XMLElement> sourceElement;
	private final Type parentType;
	

	public FlatMapTransformer(TransformerMetadata metadata, ReactiveParameters parameters, ReactiveSource source, Optional<XMLElement> sourceElement, Type parentType) {
		this.source = source;
		this.metadata = metadata;
		this.sourceElement = sourceElement;
		this.parentType = parentType;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.concatMap(item->source.execute(context,  Optional.of(item.message())));
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
