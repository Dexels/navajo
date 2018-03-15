package com.dexels.navajo.reactive.pubsub.transformer;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.replication.api.ReplicationMessageParser;

import io.reactivex.FlowableTransformer;

public class ReplicationMessageParseTransformer implements ReactiveTransformer {


	private final ReplicationMessageParser parser;
	private TransformerMetadata metadata;

	public ReplicationMessageParseTransformer(TransformerMetadata metadata, ReplicationMessageParser parser) {
		this.metadata = metadata;
		this.parser = parser;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return d->d
				.map(b->DataItem.of(parser.parseBytes(b.data()).message()));
//				.doOnRequest(l->System.err.println("Requested: "+l));
		
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
