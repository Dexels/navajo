package com.dexels.navajo.reactive.transformer.other;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class SortTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;

	public SortTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		return e->e.sorted((o1,o2)->{
//			TODO fix
			ReactiveResolvedParameters parms = parameters.resolve(context, current, param, metadata);
			List<Operand> resolved = parms.unnamedParameters().stream().collect(Collectors.toList()); //  .findFirst().orElse(Operand.ofInteger(0)).integerValue(); //parms.paramInteger("count");

			return 0;
		});
	}

	private Comparator<DataItem> createComparator(StreamScriptContext context) {
		return (o1,o2)->{
			List<Operand> parms1 = parameters.resolve(context, Optional.of(o1.message()), ImmutableFactory.empty(), metadata).unnamedParameters();
			List<Operand> parms2 = parameters.resolve(context, Optional.of(o2.message()), ImmutableFactory.empty(), metadata).unnamedParameters();
//			parms1.un
			return 0;
		};
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
