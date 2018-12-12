package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class ReduceTransformer implements ReactiveTransformer {

	private Function<StreamScriptContext, Function<DataItem, DataItem>> reducers;

	private final TransformerMetadata metadata;

	private final ReactiveParameters parameters;

	private final static Logger logger = LoggerFactory.getLogger(ReduceTransformer.class);

	
	public ReduceTransformer(TransformerMetadata metadata,ReactiveParameters parameters) {
		this.metadata = metadata;
		this.parameters = parameters;
//		 Function<StreamScriptContext,Function<DataItem,DataItem>> reducers
	}
	@SuppressWarnings("unchecked")
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current, param, metadata);


		return flow->{
//			Function<DataItem,DataItem> reducer;
			ContextExpression seed = parameters.unnamed.get(0);
			ContextExpression reducer = parameters.unnamed.get(1);
			Function<StreamScriptContext,Function<DataItem,DataItem>> seedFunction = (Function<StreamScriptContext,Function<DataItem,DataItem>>) seed.apply(null, current, Optional.of(param));
			Function<StreamScriptContext,Function<DataItem,DataItem>> reduceFunction = (Function<StreamScriptContext,Function<DataItem,DataItem>>) reducer.apply(null, current, Optional.of(param));
			System.err.println("Seed: "+seedFunction.getClass());
			try {
				Function<DataItem,DataItem> seedRes =seedFunction.apply(context);
				Function<DataItem,DataItem> reduceRes = reduceFunction.apply(context);
				return flow.map(it->it.message())
						.doOnNext(e->System.err.println(">>>>>>"+ImmutableFactory.getInstance().describe(e)))
						.reduce(seedRes.apply(DataItem.of(ImmutableFactory.empty())).message(), (item,acc)->{
							logger.info("Reduce: Acc: {} Item: {}",ImmutableFactory.getInstance().describe(acc),ImmutableFactory.getInstance().describe(item));
							return reduceRes.apply(DataItem.of(acc, item)).message();
//							return reduceRes.apply(DataItem.of(item, acc));
//							return (DataItem)reducer.apply(context.getInput().blockingGet(), Optional.of(item), Optional.of(acc));
						}).map(e->DataItem.of((ImmutableMessage)e))
						.toFlowable();
			} catch (Exception e1) {
				return Flowable.error(e1);
			}

			
//			try {
//				reducer = reducers.apply(context);
//				flow = flow.reduce(DataItem.of(ImmutableFactory.empty()), (state,message)->reducer.apply(DataItem.of(message.message(), state.stateMessage())))
//						.map(d->DataItem.of(ImmutableFactory.empty(),d.stateMessage()))
//						.toFlowable();
//				if(debug) {
//					flow = flow.doOnNext(dataitem->{
//						logger.info("After record: {}",ImmutableFactory.getInstance().describe(dataitem.stateMessage()));
//					});
//				}
//				return flow;
//			} catch (Exception e) {
//				logger.error("Error: ", context);
//			}
//			return flow;
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
