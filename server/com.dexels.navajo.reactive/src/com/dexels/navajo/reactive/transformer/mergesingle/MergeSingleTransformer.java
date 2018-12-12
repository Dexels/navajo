package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class MergeSingleTransformer implements ReactiveTransformer {

	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joiner;
	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;

	public MergeSingleTransformer(TransformerMetadata metadata, ReactiveParameters parameters,Function<StreamScriptContext,Function<DataItem,DataItem>> joiner) {
		this.joiner = joiner;
		this.metadata = metadata;
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		ReactiveResolvedParameters params = parameters.resolve(context, current, param, metadata);
		ReactiveSource source = (ReactiveSource) params.unnamedParameters().stream().findFirst().orElseThrow(()->new RuntimeException("Missing source"));
		return flow->flow.map(item->item.withStateMessage(current.orElse(ImmutableFactory.empty()))).flatMap(item->{
			Flowable<DataItem> sourceStream = source.execute(context,  Optional.of(item.message()),item.stateMessage())
					.doOnNext(dataitem->{
						System.err.println("");
					});
			return sourceStream
				.map(reducedItem->joiner.apply(context).apply(
						DataItem.of(item.message(), reducedItem.stateMessage())
						)
				);
		},false,10);
				
				
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
