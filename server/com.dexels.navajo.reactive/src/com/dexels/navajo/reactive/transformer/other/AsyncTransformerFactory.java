package com.dexels.navajo.reactive.transformer.other;

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
import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.pubsub.rx2.api.TopicPublisher;
import com.dexels.pubsub.rx2.api.TopicSubscriber;

import io.reactivex.functions.Function;

public class AsyncTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	private TopicPublisher topicPublisher;
    public void setTopicSubscriber(TopicPublisher topicPublisher, Map<String,Object> settings) {
        this.topicPublisher = topicPublisher;
    }

    public void clearTopicSubscriber(TopicSubscriber topicSubscriber) {
        this.topicPublisher = null;
    }

	@Override
	public ReactiveTransformer build(String relativePath, List<ReactiveParseProblem> problems, 
			ReactiveParameters parameters,
			Optional<XMLElement> xmlElement,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput) {
		
//		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), reducerSupplier,useGlobalInput);
//		ReactiveScriptParser.parseTransformationsFromChildren(relativePath, problems, xml, sourceSupplier, factorySupplier, reducerSupplier, transformers, reducers, useGlobalInput);
		//

		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("MergeSingleTransformerFactory: Can't build without XML element"));
		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), reducerSupplier,useGlobalInput);
		Optional<ReactiveSource> subSource;
		try {
			subSource = ReactiveScriptParser.findSubSource(relativePath, xml, problems, sourceSupplier, factorySupplier,reducerSupplier,transformers,reducers,useGlobalInput);
		} catch (Exception e) {
			throw new ReactiveParseException("Unable to parse sub source in xml: "+xml,e);
		}
		if(!subSource.isPresent()) {
			throw new NullPointerException("Missing sub source in xml: "+xml);
		}
		ReactiveSource sub = subSource.get();
		if(!sub.finalType().equals(DataItem.Type.SINGLEMESSAGE)) {
			throw new IllegalArgumentException("Wrong type of sub source: "+sub.finalType()+ ", reduce or first maybe? It should be: "+Type.SINGLEMESSAGE+" at line: "+xml.getStartLineNr()+" xml: \n"+xml);
		}
//		return new MergeSingleTransformer(this,parameters,sub, joinermapper);
				return new AsyncTransformer(this,parameters,sub, joinermapper);
	}


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
