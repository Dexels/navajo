package com.dexels.navajo.reactive.pubsub.transformer;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.replication.api.ReplicationMessageParser;

import io.reactivex.FlowableTransformer;

public class ReplicationMessageParseTransformer implements ReactiveTransformer {


	private final ReplicationMessageParser parser;
	private final TransformerMetadata metadata;
	private ReactiveParameters parameters;

	public ReplicationMessageParseTransformer(TransformerMetadata metadata, ReplicationMessageParser parser, ReactiveParameters parameters) {
		this.metadata = metadata;
		this.parameters = parameters;
		this.parser = parser;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current, param, metadata);
		
		return d->d
				.map(b->DataItem.of(parser.parseBytes(parms.optionalString("source"), b.data()).message()));
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}


}
