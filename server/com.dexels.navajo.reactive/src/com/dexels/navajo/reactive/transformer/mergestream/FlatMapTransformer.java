package com.dexels.navajo.reactive.transformer.mergestream;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class FlatMapTransformer implements ReactiveTransformer {

	private final ReactiveSource source;
	private final TransformerMetadata metadata;
	private final Optional<XMLElement> sourceElement;
	private final Type parentType;
	private final ReactiveParameters parameters;
	

	public FlatMapTransformer(TransformerMetadata metadata, ReactiveParameters parameters, ReactiveSource source, Optional<XMLElement> sourceElement, Type parentType) {
		this.source = source;
		this.metadata = metadata;
		this.sourceElement = sourceElement;
		this.parentType = parentType;
		this.parameters = parameters;

	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
//		parameters.resolveNamed(context, currentMessage, paramMessage, validator, sourceElement, sourcePath)
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, sourceElement, "");
		int parallel = resolved.optionalInteger("parallel").orElse(1);
		if(parallel == 1) {
			return flow->flow
					.map(item->item.withStateMessage(current.orElse(ImmutableFactory.empty())))
					.concatMap(item->source.execute(context,  Optional.of(item.message())));
		} else {
			return flow->flow
					.map(item->item.withStateMessage(current.orElse(ImmutableFactory.empty())))
					.flatMap(item->source.execute(context,  Optional.of(item.message())),parallel);
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
