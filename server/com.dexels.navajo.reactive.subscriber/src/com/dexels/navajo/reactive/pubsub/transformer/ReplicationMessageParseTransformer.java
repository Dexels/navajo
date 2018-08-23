package com.dexels.navajo.reactive.pubsub.transformer;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.replication.api.ReplicationMessageParser;

import io.reactivex.FlowableTransformer;

public class ReplicationMessageParseTransformer implements ReactiveTransformer {


	private final ReplicationMessageParser parser;
	private final TransformerMetadata metadata;
	private final Optional<XMLElement> sourceElement;

	public ReplicationMessageParseTransformer(TransformerMetadata metadata, ReplicationMessageParser parser, Optional<XMLElement> sourceElement) {
		this.metadata = metadata;
		this.parser = parser;
		this.sourceElement = sourceElement;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return d->d
				.map(b->DataItem.of(parser.parseBytes(b.data()).message()));
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
