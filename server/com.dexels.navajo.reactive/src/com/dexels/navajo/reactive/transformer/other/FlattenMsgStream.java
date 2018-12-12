package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FlattenMsgStream implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	private final Function<StreamScriptContext, Function<DataItem, DataItem>> joiner;
	
	public FlattenMsgStream(TransformerMetadata metadata, ReactiveParameters parameters, Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.joiner = joinermapper;
	}



	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
			Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current,param, metadata);
		int parallel = parms.optionalInteger("parallel").orElse(1);
		boolean inOrder = parms.optionalBoolean("inOrder").orElse(false);
		try {
			Function<DataItem,DataItem> fi = joiner.apply(context);
//			return flow->flow.parallel()..map(fi).sequential();

			if (parallel < 2) {
				return flow->flow.concatMap(e->e.messageStream().map(DataItem::of).observeOn(Schedulers.io()).map(fi));
			} else if(inOrder) {
				return flow->flow.concatMapEager(e->e.messageStream().map(DataItem::of).observeOn(Schedulers.io()).map(fi),parallel,3);
			} else {
				return flow->flow.flatMap(e->e.messageStream().map(DataItem::of).observeOn(Schedulers.io()).map(fi),parallel);
			}
		} catch (Exception e1) {
			return flow->Flowable.error(e1);
		}
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
