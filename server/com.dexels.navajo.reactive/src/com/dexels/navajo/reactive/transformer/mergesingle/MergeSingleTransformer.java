package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class MergeSingleTransformer implements ReactiveTransformer {

	private final ReactiveSource source;
	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joiner;
	private final TransformerMetadata metadata;
	private final Optional<XMLElement> sourceElement;

	public MergeSingleTransformer(TransformerMetadata metadata, ReactiveParameters parameters, ReactiveSource source, Function<StreamScriptContext,Function<DataItem,DataItem>> joiner,Optional<XMLElement> sourceElement) {
		this.source = source;
		this.joiner = joiner;
		this.metadata = metadata;
		this.sourceElement = sourceElement;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.flatMap(item->{
			Flowable<DataItem> sourceStream = source.execute(context,  Optional.of(item.message()))
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

	@Override
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}

}
