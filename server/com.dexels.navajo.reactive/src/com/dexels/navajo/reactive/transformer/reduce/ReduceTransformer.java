package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.reactive.api.ReactiveParameterException;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class ReduceTransformer implements ReactiveTransformer {


	private final TransformerMetadata metadata;
	private Function<StreamScriptContext,Function<DataItem,DataItem>> seedFunction;
	private Function<StreamScriptContext,Function<DataItem,DataItem>> reduceFunction;

	
	private static final Logger logger = LoggerFactory.getLogger(ReduceTransformer.class);
	private final ReactiveParameters parameters;

	
	@SuppressWarnings("unchecked")
	public ReduceTransformer(TransformerMetadata metadata,ReactiveParameters parameters) {
		this.metadata = metadata;
		this.parameters = parameters;
		if(parameters.unnamed.size()!=2) {
			 throw new ReactiveParameterException("Reduce needs two unnamed parameters");
		}
		ContextExpression seed = parameters.unnamed.get(0);
		ContextExpression reducer = parameters.unnamed.get(1);
		seedFunction = (Function<StreamScriptContext,Function<DataItem,DataItem>>) seed.apply(null, Optional.empty(),  Optional.empty()).value;
		reduceFunction = (Function<StreamScriptContext,Function<DataItem,DataItem>>) reducer.apply(null,  Optional.empty(),  Optional.empty()).value;

	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		try {
			Function<DataItem,DataItem> seedRes = seedFunction.apply(context);
			Function<DataItem,DataItem> reduceRes = reduceFunction.apply(context);
			return flow->{
				try {
					return flow.map(it->it.message())
							.reduce(seedRes.apply(DataItem.of(ImmutableFactory.empty())).message(),
								(acc,item)->reduceRes.apply(DataItem.of(item, acc)).message()
							).map(e->DataItem.of((ImmutableMessage)e))
							.toFlowable();
				} catch (Exception e1) {
					return Flowable.error(e1);
				}
			};
		} catch (Exception e2) {
			return flow->Flowable.error(e2);
		}
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
