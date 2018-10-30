package com.dexels.navajo.reactive.stored;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;
import com.dexels.replication.impl.json.ReplicationJSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.reactivex.Flowable;

public class InputStreamSource implements ReactiveSource {

	private final ReactiveParameters parameters;
	private final String relativePath;
	private final Optional<XMLElement> sourceElement;
	private final Type finalType;
	private final List<ReactiveTransformer> transformers;
	private final SourceMetadata metadata;

	
	
	public InputStreamSource(SourceMetadata metadata,  ReactiveParameters params, String relativePath, Optional<XMLElement> x,DataItem.Type finalType,List<ReactiveTransformer> transformers) {
		this.metadata = metadata;
		this.parameters = params;
		this.relativePath = relativePath;
		this.sourceElement = x;
		this.finalType = finalType;
		this.transformers = transformers;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters rsp = parameters.resolveNamed(context, current, ImmutableFactory.empty(), metadata, sourceElement, relativePath);
		
		ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false); //.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false);
		String cp = rsp.paramString("classpath",()->"");
		final InputStream fis =  getClass().getClassLoader().getResourceAsStream(cp);
		Iterator<ObjectNode> node;
		try {
			node = objectMapper.readerFor(ObjectNode.class).readValues(fis);
			Flowable<DataItem> flow = Flowable.fromIterable(()->node).map(on->DataItem.of(ReplicationJSON.parseJSON(on).message()));
			for (ReactiveTransformer trans : transformers) {
				flow = flow.compose(trans.execute(context,current));
			}
			return flow;
		} catch (IOException e1) {
			return Flowable.error(e1);
		}	

	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public boolean streamInput() {
		return false;
	}

}
