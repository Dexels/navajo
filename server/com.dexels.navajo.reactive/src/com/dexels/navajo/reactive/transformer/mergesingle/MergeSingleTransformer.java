package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		System.err.println("unnamedcount: "+params.unnamedParameters().size());
		for (Operand oo : params.unnamedParameters()) {
			System.err.println("Operand: "+oo.type+" >> "+oo.value);
		}
		List<Operand> mappers = params.unnamedParameters().stream().skip(1).collect(Collectors.toList());
		ReactivePipe source = (ReactivePipe) params.unnamedParameters()
				.stream()
				.findFirst()
				.map(e->e.value)
				.orElseThrow(()->new RuntimeException("Missing source"));
		
			return flow->flow.map(outerItem->outerItem.withStateMessage(current.orElse(ImmutableFactory.empty())))
				.flatMap(item->{
					System.err.println(">>> "+ImmutableFactory.getInstance().describe(item.stateMessage()));
					System.err.println(">|> "+ImmutableFactory.getInstance().describe(item.message()));
					Function<? super DataItem, ? extends DataItem> joiner = merge(context,item,mappers);
					return source.execute(context,  Optional.of(item.message()), item.stateMessage())
//						.map(e->e.message().merge(item.message(), Optional.empty()))
						.firstElement()
						
						.map(ee->joiner.apply(ee.withStateMessage(item.message())))
	//					.map(e->merge(item).apply(e))
						.toFlowable();
				}
				
					
//							.map(reducedItem->joiner.apply(context)
//							.apply(DataItem.of(item.message(), reducedItem.message()))
//							)
		,false,10);
//		.map(e->DataItem.of(e));
				
				
	}
	
	private static final Function<? super DataItem, ? extends DataItem> merge(StreamScriptContext context,DataItem with,List<Operand> mappers) {
		if(mappers.isEmpty()) {
			return (a)->DataItem.of(a.message().merge(with.message(),Optional.empty()));
		}
		List<Function<DataItem,DataItem>> ll = mappers.stream()
				.map(e->(Function<StreamScriptContext,Function<DataItem,DataItem>>)e.value)
				.map(e->e.apply(context))
				.collect(Collectors.toList());
		return mrg->{
			DataItem result = mrg;
			for (Function<DataItem, DataItem> function : ll) {
				result = function.apply(result);
			}
			return result;
		};
//		return (a)->DataItem.of(a.message().merge(with.message(),Optional.empty()));
		
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
