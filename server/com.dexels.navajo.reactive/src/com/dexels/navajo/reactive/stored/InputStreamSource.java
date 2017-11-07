package com.dexels.navajo.reactive.stored;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.impl.json.ReplicationJSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.reactivex.Flowable;

public class InputStreamSource implements ReactiveSource, ParameterValidator {

	private final ReactiveParameters parameters;
	private final String relativePath;
	private final XMLElement sourceElement;
	private final Type finalType;
	private final List<ReactiveTransformer> transformers;

	
	private final static Logger logger = LoggerFactory.getLogger(InputStreamSource.class);

	
	public InputStreamSource(ReactiveParameters params, String relativePath, XMLElement x,DataItem.Type finalType,List<ReactiveTransformer> transformers) {
		this.parameters = params;
		this.relativePath = relativePath;
		this.sourceElement = x;
		this.finalType = finalType;
		this.transformers = transformers;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ReplicationMessage> current) {
		ReactiveResolvedParameters rsp = parameters.resolveNamed(context, current, Optional.empty(), this, sourceElement, relativePath);
		
		ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false); //.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,false);
//		String path = rsp.paramString("path","");
		String cp = rsp.paramString("classpath","");
//		!"".equals(path)? new FileInputStream(path):
		System.err.println("CP : "+cp);
		URL resource = getClass().getClassLoader().getResource(cp);
		System.err.println("ResourceURL: "+resource);
		final InputStream fis =  getClass().getClassLoader().getResourceAsStream(cp);
		Iterator<ObjectNode> node;
		try {
			node = objectMapper.readerFor(ObjectNode.class).readValues(fis);
			Flowable<DataItem> flow = Flowable.fromIterable(()->node).map(on->DataItem.of(ReplicationJSON.parseJSON(on))).doOnNext(e->System.err.println("ITEM!"));
			for (ReactiveTransformer trans : transformers) {
				flow = flow.compose(trans.execute(context));
			}
			return flow;
		} catch (IOException e1) {
			return Flowable.error(e1);
		}
		
		
//		Flowable<DataItem> flow = Flowable.fromIterable(()->node).map(e->ReplicationJSON.parseJSON(e)).map(DataItem::of);
				
		
//		MappingIterator<ObjectNode> iterator = objectMapper.readerFor(ObjectNode.class).readValues(fis);
//		while (iterator.hasMoreValues()) {
//		   ObjectNode value = iterator.nextValue();
//		   ReplicationJSON.parseJSON(value);
//		}
		
			// TODO parse individual...
//			Flowable<DataItem> flow = Flowable.fromIterable( ()->new Iterator<ReplicationMessage>() {
//				ReplicationMessage current = null;
//				@Override
//				public boolean hasNext() {
//					try {
//						System.err.println("HASNEXT");
//						current = ReplicationJSON.parseReplicationMessage(fis, objectMapper);
//						return current!=null;
//					} catch (IOException e) {
//						logger.error("Error: ", e);
//					}
//					try {
//						fis.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					return false;
//				}
//
//				@Override
//				public ReplicationMessage next() {
//					System.err.println("NEXT");
//					ReplicationMessage r = current;
//					current = null;
//					return r;
//				}}).map(DataItem::of);
//			
			
			

	}

	@Override
	public Type dataType() {
		return Type.MESSAGE;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"path","classpath"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> types = new HashMap<>();
		types.put("path", Property.STRING_PROPERTY);
		types.put("classpath", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(types));
	}

}
