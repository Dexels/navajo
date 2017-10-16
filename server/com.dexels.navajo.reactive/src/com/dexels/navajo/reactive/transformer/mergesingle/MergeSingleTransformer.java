package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class MergeSingleTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private final ReactiveSource source;
//	private final Function<StreamScriptContext, BiFunction<ReplicationMessage, ReplicationMessage, ReplicationMessage>> reducer;
	private final Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> reducer;
	private final Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> joiner;

	public MergeSingleTransformer(ReactiveParameters parameters,ReactiveSource source,  Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> reducer, Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> joiner) {
		this.parameters = parameters;
		this.source = source;
		this.reducer = reducer;
		this.joiner = joiner;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.flatMap(item->{
			Flowable<DataItem> sourceStream = source.execute(context,  Optional.of(item.message()));
			if(reducer.isPresent()) {
				sourceStream = sourceStream.reduce(DataItem.of(ReactiveScriptParser.empty()), (a,i)->reducer.get().apply(context).apply(a, Optional.of(i))).toFlowable();
			}
			return sourceStream
				.map(reducedItem->joiner.apply(context).apply(item,Optional.of(reducedItem)));
		},false,10);
				
				
	}

}
