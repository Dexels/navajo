package com.dexels.navajo.reactive.transformer.single;

import java.util.Collections;
import java.util.Optional;

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

public class SingleMessageTransformer implements ReactiveTransformer {

	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joinerMapper;
	private final ReactiveParameters parameters;
	private final Optional<XMLElement> source;
	private final String path;
	private TransformerMetadata metadata;
	
	public SingleMessageTransformer(TransformerMetadata metadata, ReactiveParameters parameters, Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper, Optional<XMLElement> xml, String path) {
		this.parameters = parameters;
		this.joinerMapper = joinermapper;
		this.source = xml;
		this.path = path;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, source, path);
		boolean debug = parms.paramBoolean("debug", ()->false);
		
		FlowableTransformer<DataItem, DataItem> transformer = debug ? 
				   flow->flow.map(item->joinerMapper.apply(context).apply(item)).doOnNext(this::debugMessage)
				:  flow->flow.map(item->joinerMapper.apply(context).apply(item));
		return transformer;
	}

	private void debugMessage(DataItem di) {
		System.err.println("Message:DEBUG: "+di.message().flatValueMap(true, Collections.emptySet(), ""));
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public Optional<XMLElement> sourceElement() {
		return this.source;
	}
	
}
