package com.dexels.navajo.reactive.kafka;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.kafka.api.OffsetQuery;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;
import com.dexels.pubsub.rx2.api.TopicSubscriber;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;

import io.reactivex.Flowable;


public class SubscriberReactiveSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final TopicSubscriber topicSubcriber;
	private final Optional<OffsetQuery> offsetQuery;
	private final SourceMetadata metadata;
	
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriberReactiveSource.class);


	public SubscriberReactiveSource(SourceMetadata metadata, TopicSubscriber topicSubscriber, Optional<OffsetQuery> offsetQuery, ReactiveParameters params,
		 Map<String, Object> subscriberSettings) {
		this.metadata = metadata;
		this.params = params;
		this.topicSubcriber = topicSubscriber;
		this.offsetQuery = offsetQuery;
	}

	
	private String createDefaultEndTag(String topic) {
		Map<Integer,Long> mm = offsetQuery.orElseThrow(()->new RuntimeException("Missing offsetquery.")).partitionOffsets(topic);
		
		return offsetQuery.get().encodeTopicTag(partition->mm.get(partition),new ArrayList<>(mm.keySet()));
	}
	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolvedParams = params.resolve(context, current, param, metadata);
		String tenant = context.getTenant();
		String topic = resolvedParams.paramString("topic");
		String group = tenant+"-"+context.deployment()+"-"+UUID.randomUUID().toString();  //+ resolvedParams.paramString("group");
		String from = resolvedParams.paramString("from",()->"0:0");
		String to = resolvedParams.paramString("to",()->createDefaultEndTag(topic));
		AtomicInteger outMessages = new AtomicInteger();

		String assembledTopic = tenant + "-"+context.deployment()+"-"+topic;
		
		
		ReplicationFactory.setInstance(new FallbackReplicationMessageParser(true));
		AtomicLong totalRequest = new AtomicLong();
		Flowable<DataItem> flow = Flowable.fromPublisher(topicSubcriber.subscribeSingleRange(assembledTopic, group, from, to))
			.concatMap(e->Flowable.fromIterable(e))
			.doOnNext(e->totalRequest.decrementAndGet())
			.doOnRequest(e->totalRequest.addAndGet(e))
			.map(e->DataItem.of(e.value()))
			.doOnNext(e->outMessages.incrementAndGet())
			.doOnComplete(()->logger.info("Subscriber total: "+outMessages.get()));
		
		return flow;
			
	}

	@Override
	public boolean streamInput() {
		return false;
	}


	@Override
	public Type sourceType() {
		return Type.DATA;
	}

	public ReactiveParameters parameters() {
		return params;
	}

}

