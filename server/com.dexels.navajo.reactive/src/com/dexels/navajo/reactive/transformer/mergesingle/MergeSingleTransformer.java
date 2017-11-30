package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class MergeSingleTransformer implements ReactiveTransformer {

	private final ReactiveSource source;
//	private final Function<StreamScriptContext, BiFunction<ReplicationMessage, ReplicationMessage, ReplicationMessage>> reducer;
	private final Optional<Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>>> reducer;
	private final Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> joiner;

	public MergeSingleTransformer(ReactiveSource source,  Optional<Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>>> reducer, Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> joiner) {
		this.source = source;
		this.reducer = reducer;
		this.joiner = joiner;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.flatMap(item->{
			Flowable<DataItem> sourceStream = source.execute(context,  Optional.of(item.message()));
			if(reducer.isPresent()) {
				sourceStream = sourceStream.reduce(DataItem.of(ReactiveScriptParser.empty()), (a,i)->reducer.get().apply(context).apply(a, i)).toFlowable();
			}
			return sourceStream
				.map(reducedItem->joiner.apply(context).apply(item,reducedItem));
		},false,10);
				
				
	}

	@Override
	public Type inType() {
		return Type.MESSAGE;
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

}
