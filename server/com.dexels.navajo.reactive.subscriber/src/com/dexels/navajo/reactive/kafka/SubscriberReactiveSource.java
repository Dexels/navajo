package com.dexels.navajo.reactive.kafka;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.pubsub.rx2.api.TopicSubscriber;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


public class SubscriberReactiveSource implements ReactiveSource, ParameterValidator {

	private final Type finalType;
	private final ReactiveParameters params;
	private final XMLElement sourceElement;
	private final String relativePath;
	private final TopicSubscriber topicSubcriber;
	
// 	<source.kafka.message [resource="topicscubscriber"] topic="bla-bla" group="bla" from="bla-bla:0">

	public SubscriberReactiveSource(TopicSubscriber topicSubscriber, ReactiveParameters params, String relativePath, XMLElement x, Type finalType,
			List<ReactiveTransformer> transformers, Function<String, ReactiveMerger> reducerSupplier,
			Function<String, ReactiveMapper> mapperSupplier) {
		this.finalType = finalType;
		this.params = params;
		this.sourceElement = x;
		this.relativePath = relativePath;
		this.topicSubcriber = topicSubscriber;
	}



	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolvedParams = params.resolveNamed(context, current, Optional.empty(), this, sourceElement, relativePath);
		String topic = resolvedParams.paramString("topic");
		String group = resolvedParams.paramString("group");
		String from = resolvedParams.paramString("from");
		String to = resolvedParams.paramString("to");
		String tenant = context.tenant;
//		context.
		return Flowable.fromPublisher(topicSubcriber.subscribeSingleRange(topic, group, from, to))
			.concatMap(e->Flowable.fromIterable(e))
			.map(e->ReplicationFactory.getInstance().parseBytes(e.value()))
			.map(e->DataItem.of(e.message()));
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
		return Optional.of(Arrays.asList(new String[] {"topic","group","from","to"}));
	}



	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {"topic","group"}));
	}



	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> types = new HashMap<>();
		types.put("topic", "string");
		types.put("group", "string");
		types.put("from", "string");
		types.put("to", "string");
		return Optional.of(Collections.unmodifiableMap(types));
	}

}
