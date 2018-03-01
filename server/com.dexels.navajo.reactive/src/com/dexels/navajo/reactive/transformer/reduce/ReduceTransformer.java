package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class ReduceTransformer implements ReactiveTransformer {

	private Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> reducers;

//	private final ReactiveParameters parameters;
	
	private final static Logger logger = LoggerFactory.getLogger(ReduceTransformer.class);

	
	public ReduceTransformer(Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> reducers,ReactiveParameters parameters) {
		this.reducers = reducers;
	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		
		return flow->{
			BiFunction<DataItem, Optional<DataItem>, DataItem> reducer;
			try {
				reducer = reducers.apply(context);
				return flow.reduce(DataItem.of(ReactiveScriptParser.empty()),(item,other)->reducer.apply(item, Optional.of(other))).toFlowable();
			} catch (Exception e) {
				logger.error("Error: ", context);
			}
			return flow;
		};
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE})) ;
	}

	@Override
	public Type outType() {
		return Type.SINGLEMESSAGE;
	}

}
