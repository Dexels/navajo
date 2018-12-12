package com.dexels.navajo.reactive.stored;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;
import com.dexels.replication.impl.json.ReplicationJSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.reactivex.Flowable;

public class InputStreamSource implements ReactiveSource {

	private final ReactiveParameters parameters;
	private final SourceMetadata metadata;

	
	
	public InputStreamSource(SourceMetadata metadata,  ReactiveParameters params) {
		this.metadata = metadata;
		this.parameters = params;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters rsp = parameters.resolve(context, current,param,metadata);
		
		ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false); //.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false);
		String cp = rsp.paramString("classpath",()->"");
		final InputStream fis =  getClass().getClassLoader().getResourceAsStream(cp);
		Iterator<ObjectNode> node;
		try {
			node = objectMapper.readerFor(ObjectNode.class).readValues(fis);
			Flowable<DataItem> flow = Flowable.fromIterable(()->node).map(on->DataItem.of(ReplicationJSON.parseJSON(Optional.empty(), on).message()));
			return flow;
		} catch (IOException e1) {
			return Flowable.error(e1);
		}	

	}

	@Override
	public boolean streamInput() {
		return false;
	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}

}
