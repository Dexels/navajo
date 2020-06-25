package com.dexels.navajo.reactive.source.topology;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class FilterTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public FilterTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,Optional<ImmutableMessage> current, ImmutableMessage param) {
		return item->Flowable.error(()->new ReactiveParseException("Group transformer shouldn't be executed"));

	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public ReactiveParameters parameters() {
		return parameters;
	}



}
