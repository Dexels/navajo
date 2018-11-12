package com.dexels.navajo.reactive.transformer.reduce;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class ScanTransformer implements ReactiveTransformer {

	private Function<StreamScriptContext, Function<DataItem, DataItem>> reducers;

	private TransformerMetadata metadata;

	private final Optional<XMLElement> sourceElement;

//	private final ReactiveParameters parameters;
	
	private final static Logger logger = LoggerFactory.getLogger(ScanTransformer.class);

	
	public ScanTransformer(TransformerMetadata metadata, Function<StreamScriptContext,Function<DataItem,DataItem>> reducers,ReactiveParameters parameters,Optional<XMLElement> sourceElement) {
		this.metadata = metadata;
		this.reducers = reducers;
		this.sourceElement = sourceElement;
	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		
		return flow->{
			Function<DataItem,DataItem> reducer;
			try {
				reducer = reducers.apply(context);
				return flow.scan(DataItem.of(ImmutableFactory.empty()), (state,message)->reducer.apply(DataItem.of(message.message(), state.stateMessage())));
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
	@Override
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}

}
