package com.dexels.navajo.reactive.pubsub.transformer;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;

import io.reactivex.functions.Function;

public class ReplicationMessageParseTransformerFactory implements ReactiveTransformerFactory {

	FallbackReplicationMessageParser parser = new FallbackReplicationMessageParser(true);

	@Override
	public ReactiveTransformer build(String relativePath, Optional<XMLElement> xml,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier) {
		return new ReplicationMessageParseTransformer(parser);
	}
}
