package com.dexels.navajo.reactive.transformer.reduce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class ReduceTransformer implements ReactiveTransformer {

	private Function<StreamScriptContext, BiFunction<DataItem, DataItem, DataItem>> reducers;
	
	private final static Logger logger = LoggerFactory.getLogger(ReduceTransformer.class);

	
	public ReduceTransformer(Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> reducers) {
		this.reducers = reducers;
	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->{
			BiFunction<DataItem, DataItem, DataItem> reducer;
			try {
				reducer = reducers.apply(context);
				return flow.reduce(DataItem.of(ReactiveScriptParser.empty()),reducer).toFlowable();
			} catch (Exception e) {
				logger.error("Error: ", context);
			}
			return flow;
		};
	}

	@Override
	public Type inType() {
		return Type.MESSAGE;
	}

	@Override
	public Type outType() {
		return Type.SINGLEMESSAGE;
	}

}
