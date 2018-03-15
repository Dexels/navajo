package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class FilterTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public FilterTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return e->e.filter(item->{
			ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.of(item.message()), ImmutableFactory.empty(), metadata, Optional.empty(), "");
			return parms.paramBoolean("filter");
		});
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}


}
