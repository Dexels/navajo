package com.dexels.navajo.reactive.transformer.single;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SingleMessageTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> joinerMapper;
	
	private final static Logger logger = LoggerFactory.getLogger(SingleMessageTransformer.class);

	public SingleMessageTransformer(ReactiveParameters parameters, Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> joinermapper) {
		this.parameters = parameters;
		this.joinerMapper = joinermapper;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.map(item->joinerMapper.apply(context).apply(item, Optional.empty()));
//				.doOnNext(e->logger.info("ITEM: "+e.message().toFlatString(ReplicationFactory.getInstance())));
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
