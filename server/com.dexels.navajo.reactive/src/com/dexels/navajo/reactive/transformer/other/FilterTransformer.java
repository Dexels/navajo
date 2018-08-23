package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
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
	private final Optional<XMLElement> sourceElement;

	public FilterTransformer(TransformerMetadata metadata, ReactiveParameters parameters,Optional<XMLElement> sourceElement) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.sourceElement = sourceElement;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return e->e.filter(item->{
			ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.of(item.message()), item.stateMessage(), metadata, Optional.empty(), "");
			return parms.paramBoolean("filter");
		});
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
