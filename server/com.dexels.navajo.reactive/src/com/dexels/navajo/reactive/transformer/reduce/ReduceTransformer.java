package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class ReduceTransformer implements ReactiveTransformer {

	private Function<StreamScriptContext, Function<DataItem, DataItem>> reducers;

	private TransformerMetadata metadata;

	private final ReactiveParameters parameters;

//	private final ReactiveParameters parameters;
	
	private final static Logger logger = LoggerFactory.getLogger(ReduceTransformer.class);

	
	public ReduceTransformer(TransformerMetadata metadata, Function<StreamScriptContext,Function<DataItem,DataItem>> reducers,ReactiveParameters parameters) {
		this.metadata = metadata;
		this.reducers = reducers;
		this.parameters = parameters;
	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, Optional.empty(), "");

		boolean debug = parms.optionalBoolean("debug").orElse(false);

				return flow->{
			Function<DataItem,DataItem> reducer;
			try {
				reducer = reducers.apply(context);
				flow = flow.reduce(DataItem.of(ImmutableFactory.empty()), (state,message)->reducer.apply(DataItem.of(message.message(), state.stateMessage())))
						.map(d->DataItem.of(ImmutableFactory.empty(),d.stateMessage()))
						.toFlowable();
				
//				return flow.reduce(DataItem.of(ImmutableFactory.empty()), (state,message)->reducer.apply(DataItem.of(ImmutableFactory.empty(), state.stateMessage()))).toFlowable();
				if(debug) {
					flow = flow.doOnNext(dataitem->{
						logger.info("After record: {}",ImmutableFactory.getInstance().describe(dataitem.stateMessage()));
					});
				}
				return flow;
			} catch (Exception e) {
				logger.error("Error: ", context);
			}
			return flow;
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
