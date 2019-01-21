package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.reactive.api.ReactiveParameters;
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
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,Optional<ImmutableMessage> current, ImmutableMessage param) {
		return flow->{
			for (ContextExpression unnamed  : parameters.unnamed) {
				flow = flow.filter(item->{
					Operand result = unnamed.apply(null, Optional.of(item.message()),Optional.of(item.stateMessage()));
					return result.booleanValue();
				});
			}
			return flow;
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}



}
