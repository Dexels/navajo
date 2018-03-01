package com.dexels.navajo.reactive.pubsub.transformer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessageParser;

import io.reactivex.FlowableTransformer;

public class ReplicationMessageParseTransformer implements ReactiveTransformer {


	private final ReplicationMessageParser parser;

	public ReplicationMessageParseTransformer(ReplicationMessageParser parser) {
		this.parser = parser;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return d->d
				.map(b->DataItem.of(parser.parseBytes(b.data()).message()));
//				.doOnRequest(l->System.err.println("Requested: "+l));
		
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.DATA})) ;
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

}
