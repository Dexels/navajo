package com.dexels.navajo.reactive.transformer.persistent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.pubsub.rx2.api.TopicPublisher;
import com.dexels.pubsub.rx2.api.TopicSubscriber;

public class PersistentTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	private TopicPublisher topicPublisher;

    public void setTopicPublisher(TopicPublisher topicPublisher, Map<String,Object> settings) {
        this.topicPublisher = topicPublisher;
    }

    public void clearTopicPublisher(TopicSubscriber topicSubscriber) {
        this.topicPublisher = null;
    }
    

	@Override
	public ReactiveTransformer build(Type parentType, List<ReactiveParseProblem> problems,
			ReactiveParameters parameters, ReactiveBuildContext buildContext) {
		return new PersistentTransformer(this,problems,parameters, buildContext,topicPublisher,b,parentType);
	}

	@Override
	public PersistentTransformer build(Type parentType, String relativePath, List<ReactiveParseProblem> problems, 
			ReactiveParameters parameters,
			Optional<XMLElement> xmlElement,
			ReactiveBuildContext buildContext) {

//
//		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("Persistent Transformer: Can't build without XML element"));
////		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), buildContext);
////		List<ReactiveTransformer> subtransformer = ReactiveScriptParser.parseTransformationsFromChildren(relativePath, problems, Optional.of(xml),buildContext);
//
//		// we don't really need to parse it now, but it is good to know if there is something wrong.
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try {
//			xml.write(new OutputStreamWriter(baos));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Binary b = new Binary(baos.toByteArray());
//
//		
//		return new PersistentTransformer(this,relativePath,problems,xmlElement,parameters, buildContext,topicPublisher,b,parentType);
	}
	
//	private byte[] serializeScript(	)


	
	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.SINGLEMESSAGE,DataItem.Type.MESSAGE}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.SINGLEMESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[] {}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String, String> r = new HashMap<>();
		return Optional.of(Collections.unmodifiableMap(r));
	}

	@Override
	public String name() {
		return "async";
	}

}
