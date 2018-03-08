package com.dexels.navajo.reactive.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.kafka.api.OffsetQuery;
import com.dexels.kafka.factory.KafkaClientFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.pubsub.rx2.api.TopicSubscriber;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;


public class SubscriberReactiveSource implements ReactiveSource, ParameterValidator {

	private final Type finalType;
	private final ReactiveParameters params;
	private final Optional<XMLElement> sourceElement;
	private final String relativePath;
	private final TopicSubscriber topicSubcriber;
	private final List<ReactiveTransformer> transformers;
	private final Optional<OffsetQuery> offsetQuery;
	private final Map<String, Object> subscriberSettings;
	
// 	<source.kafka.message [resource="topicscubscriber"] topic="bla-bla" group="bla" from="bla-bla:0">

	public SubscriberReactiveSource(TopicSubscriber topicSubscriber, Optional<OffsetQuery> offsetQuery, ReactiveParameters params, String relativePath, Optional<XMLElement> x, Type finalType,
			List<ReactiveTransformer> transformers, Function<String, ReactiveMerger> reducerSupplier, Map<String, Object> subscriberSettings) {
		this.finalType = finalType;
		this.params = params;
		this.sourceElement = x;
		this.relativePath = relativePath;
		this.topicSubcriber = topicSubscriber;
		this.transformers = transformers;
		this.offsetQuery = offsetQuery;
		this.subscriberSettings = subscriberSettings;
	}

	
	private String createDefaultEndTag(String topic) {
		Map<Integer,Long> mm = offsetQuery.orElseThrow(()->new RuntimeException("Missing offsetquery.")).partitionOffsets(topic);
		
//		offsetQuery.get().encodeTopicTag(tag, partitions)
		return offsetQuery.get().encodeTopicTag(partition->mm.get(partition),new ArrayList<>(mm.keySet()));
	}
	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolvedParams = params.resolveNamed(context, current, ImmutableFactory.empty(), this, sourceElement, relativePath);
		String tenant = context.tenant;
		String topic = resolvedParams.paramString("topic");
		String group = tenant+"-"+context.deployment()+"-"+UUID.randomUUID().toString();  //+ resolvedParams.paramString("group");
		String from = resolvedParams.paramString("from",()->"0:0");
		String to = resolvedParams.paramString("to",()->createDefaultEndTag(topic));
		AtomicInteger outMessages = new AtomicInteger();

		String assembledTopic = tenant + "-"+context.deployment()+"-"+topic;
		System.err.println("ASSEMBLED: "+assembledTopic+" group: "+group+" from: "+from+" to: "+to );
		
		
		ReplicationFactory.setInstance(new FallbackReplicationMessageParser(true));
		AtomicLong totalRequest = new AtomicLong();
		
		Map<String,String> config = new HashMap<>();
		config.put("wait", "1000");
		TopicSubscriber subscriber = KafkaClientFactory.createSubscriber("aeneas1.sportlink-infra.net:9092,aeneas1.sportlink-infra.net:9093", config);

		
		
//		System.err.println("SubscriberSettings: "+subscriberSettings);
//		List<DataItem> persons = Flowable.fromPublisher(subscriber.subscribeSingleRange("KNVB-test-sportlinkkernel-PERSON", UUID.randomUUID().toString(), "0:23000", "0:24000"))
//				.concatMap(l->Flowable.fromIterable(l))
//				.map(e->ReplicationFactory.getInstance().parseBytes(e.value()))
//				.doOnNext(e->e.commit())
//				.doOnNext(e->System.err.println("ble: "+e.message().columnNames()))
//				.map(e->DataItem.of(e.message()))
//				.toList()
//				.blockingGet();
//		System.err.println("PERSONS::::: "+persons.size());
//		
		Flowable<DataItem> flow = Flowable.fromPublisher(topicSubcriber.subscribeSingleRange(assembledTopic, group, from, to))
			.concatMap(e->Flowable.fromIterable(e))
			.doOnNext(e->System.err.println("some message: "+e.key()))
			.doOnNext(e->totalRequest.decrementAndGet())
//			.filter(e->e.value()!=null)
			.doOnNext(e->System.err.println("Message detected: "+e.key()))
			.doOnRequest(e->totalRequest.addAndGet(e))
			.doOnRequest(e->System.err.println("Total Requested: "+totalRequest.get()))
			.doOnNext(e->System.err.println(">Total Requested: "+totalRequest.get()))
			.doOnCancel(()->System.err.println("Canceled!"))
//			.map(e->DataItem.of(e.value()))
//			.map(e->ReplicationFactory.getInstance().parseBytes(e.value()))
			.map(e->DataItem.of(e.value()))
			.doOnNext(e->outMessages.incrementAndGet())
			.doOnComplete(()->System.err.println("Subscriber total: "+outMessages.get()));
		
			
			
		for (ReactiveTransformer reactiveTransformer : transformers) {
			flow = flow.compose(reactiveTransformer.execute(context));
		}
		
		return flow;
			
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
		return Optional.of(Arrays.asList(new String[] {"topic","from","to"}));
	}



	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {"topic"}));
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
