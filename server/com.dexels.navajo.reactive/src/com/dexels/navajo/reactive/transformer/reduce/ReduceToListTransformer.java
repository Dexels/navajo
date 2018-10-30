package com.dexels.navajo.reactive.transformer.reduce;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class ReduceToListTransformer implements ReactiveTransformer {

	private Function<StreamScriptContext, Function<DataItem, DataItem>> reducers;

	private final TransformerMetadata metadata;

	private final ReactiveParameters parameters;

	private final Optional<XMLElement> sourceElement;

//	private final ReactiveParameters parameters;
	
	private final static Logger logger = LoggerFactory.getLogger(ReduceToListTransformer.class);

	
	public ReduceToListTransformer(TransformerMetadata metadata, Function<StreamScriptContext,Function<DataItem,DataItem>> reducers,ReactiveParameters parameters, Optional<XMLElement> sourceElement) {
		this.metadata = metadata;
		this.reducers = reducers;
		this.parameters = parameters;
		this.sourceElement = sourceElement;
	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, Optional.empty(), "");
		// MUTABLE EDITION!
			return flow->{
			try {
				flow = flow.reduce(DataItem.of(new ArrayList<>()), (state,message)->{
					state.messageList().add(message.message());
					return state;
				})
				.toFlowable();
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
	@Override
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}

}
