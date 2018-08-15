package com.dexels.navajo.reactive.transformer.mergestream;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class MergeMultiTransformer implements ReactiveTransformer {

	private final ReactiveSource source;
	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joiner;
	private TransformerMetadata metadata;

	public MergeMultiTransformer(TransformerMetadata metadata, ReactiveParameters parameters, ReactiveSource source, Function<StreamScriptContext,Function<DataItem,DataItem>> joiner) {
		this.source = source;
		this.joiner = joiner;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.map(item->source.execute(context,  Optional.of(item.message()))
					.map(stream->item.message())
				)
				.map(stream->DataItem.of(stream));
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
