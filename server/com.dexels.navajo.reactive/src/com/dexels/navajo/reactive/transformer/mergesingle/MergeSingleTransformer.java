package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class MergeSingleTransformer implements ReactiveTransformer {

//	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joiner;
	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;

	public MergeSingleTransformer(TransformerMetadata metadata, ReactiveParameters parameters, List<ReactiveParseProblem> problems) {
//		this.joiner = joiner;
		this.metadata = metadata;
		this.parameters = parameters;
//		this.joiner = context->item->item;
		if(parameters.unnamed.size()==0) {
			problems.add(ReactiveParseProblem.of("join transformer needs at least a source as first parameter"));
		}
		Optional<String> srcType = parameters.unnamed.get(0).returnType();
		if(!srcType.isPresent()) {
			problems.add(ReactiveParseProblem.of("in join transformer: Source type is unclear"));
			return;
		}
		if(!srcType.get().equals(Reactive.ReactiveItemType.REACTIVE_PIPE.toString())) {
			problems.add(ReactiveParseProblem.of("Wrong type of first argument to join, didn't expect: "+srcType.get()));
		}
		boolean isOk = parameters.unnamed
				.stream()
				.skip(1)
				.map(e->e.returnType())
				.allMatch(e->e.isPresent() && e.get().equals(Reactive.ReactiveItemType.REACTIVE_MAPPER.toString()));
		if(!isOk) {
			problems.add(ReactiveParseProblem.of("All following types should be reactive mappers"));
		}

		parameters.unnamed.stream().skip(1).map(e->e.apply().value).forEach(e->System.err.println("THING: "+e));
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		ReactiveResolvedParameters params = parameters.resolve(context, current, param, metadata);
		params.resolveAllParams();
		System.err.println("unnamedcount: "+params.unnamedParameters().size());
		for (Operand oo : params.unnamedParameters()) {
			System.err.println("Operand: "+oo.type+" >> "+oo.value);
		}
		ReactivePipe source = (ReactivePipe) params.unnamedParameters()
				.stream()
				.findFirst()
				.map(o->{
					System.err.println("<<>> PIPE? "+o.type+" val: "+o.value.getClass());
					return o;
				})
				.map(e->e.value)
				.orElseThrow(()->new RuntimeException("Missing source"));
		
			return flow->flow.map(item->item.withStateMessage(current.orElse(ImmutableFactory.empty()))
					)
				.flatMap(item->
					source.execute(context,  Optional.of(item.message()), item.stateMessage())
//							.map(reducedItem->joiner.apply(context)
//							.apply(DataItem.of(item.message(), reducedItem.message()))
//							)
		,false,10);
				
				
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
