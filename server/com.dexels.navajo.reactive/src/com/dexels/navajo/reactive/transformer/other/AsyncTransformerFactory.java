package com.dexels.navajo.reactive.transformer.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.pubsub.rx2.api.TopicPublisher;

public class AsyncTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	@SuppressWarnings("unused")
	private TopicPublisher topicPublisher;
    public void setTopicPublisher(TopicPublisher topicPublisher, Map<String,Object> settings) {
        this.topicPublisher = topicPublisher;
    }

    public void clearTopicPublisher(TopicPublisher topicSubscriber) {
        this.topicPublisher = null;
    }
    

	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems,
			ReactiveParameters parameters) {
//		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("MergeSingleTransformerFactory: Can't build without XML element"));
//		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), buildContext);
//		Optional<ReactiveSource> subSource;
//		try {
//			subSource = ReactiveScriptParser.findSubSource(relativePath, xml, problems,buildContext,Optional.of(Type.SINGLEMESSAGE));
//		} catch (Exception e) {
//			throw new ReactiveParseException("Unable to parse sub source in xml: "+xml,e);
//		}
//		if(!subSource.isPresent()) {
//			throw new NullPointerException("Missing sub source in xml: "+xml);
//		}
//		ReactiveSource sub = subSource.get();
//		if(!sub.finalType().equals(DataItem.Type.SINGLEMESSAGE)) {
//			throw new IllegalArgumentException("Wrong type of sub source: "+sub.finalType()+ ", reduce or first maybe? It should be: "+Type.SINGLEMESSAGE+" at line: "+xml.getStartLineNr()+" xml: \n"+xml);
//		}
//		return new MergeSingleTransformer(this,parameters,sub, joinermapper);
		// TODO
		return new AsyncTransformer(this,parameters, context->item->item);
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
