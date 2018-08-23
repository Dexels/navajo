package com.dexels.navajo.reactive.subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

import com.dexels.kafka.factory.KafkaClientFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.StandardTransformerMetadata;
import com.dexels.navajo.reactive.kafka.SubscriberReactiveSourceFactory;
import com.dexels.navajo.reactive.pubsub.transformer.ReplicationMessageParseTransformer;
import com.dexels.pubsub.rx2.api.TopicSubscriber;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;

import io.reactivex.Flowable;

public class TestKafka {

	public TestKafka() {
		// TODO Auto-generated constructor stub
	}

	@Test @Ignore
	public void testWeirdness() {
		Map<String,String> config = new HashMap<>();
		config.put("wait", "1000");
		FallbackReplicationMessageParser parser = new FallbackReplicationMessageParser(true);
		ReplicationFactory.setInstance(parser);
		TopicSubscriber subscriber = KafkaClientFactory.createSubscriber("someserver", config);
//		ReplicationFactory.setInstance(new FallbackReplicationMessageParser(true));
		List<DataItem> items = Flowable.fromPublisher(subscriber.subscribeSingleRange("sometopic", UUID.randomUUID().toString(), "0:23000", "0:24000"))
				.concatMap(l->Flowable.fromIterable(l))
				.map(e->ReplicationFactory.getInstance().parseBytes(e.value()))
				.doOnNext(e->e.commit())
				.map(e->DataItem.of(e.message()))
				.toList()
				.blockingGet();
		System.err.println("items::::: "+items.size());
	}
	
	@Test @Ignore
	public void testKafka() {
		Map<String,String> config = new HashMap<>();
		List<ReactiveParseProblem> problems = new ArrayList<>();
		config.put("wait", "1000");
		FallbackReplicationMessageParser parser = new FallbackReplicationMessageParser(true);
		ReplicationFactory.setInstance(parser);
		TopicSubscriber subscriber = KafkaClientFactory.createSubscriber("10.0.0.7:9092", config);
		
		SubscriberReactiveSourceFactory krsf = new SubscriberReactiveSourceFactory();
		krsf.setTopicSubscriber(subscriber,Collections.emptyMap());
		String tenant ="tenant";
		String service ="blo";
		String deployment ="develop";
		
		ReactiveTransformer rt = new ReplicationMessageParseTransformer(StandardTransformerMetadata.noParams(Type.DATA, Type.MESSAGE), parser,Optional.empty());
		List<ReactiveTransformer> transformers = Arrays.asList(new ReactiveTransformer[]{rt});
		
		ReactiveParameters params = ReactiveParameters.empty()
				.withConstant("topic", "sometopic", "string")
//				.withConstant("group", "mygroup", "string")
//				.withConstant("from", "0:10", "string")
				.withConstant("to", "0:1000", "string")
				;

		ReactiveSource rs = krsf.build("kafka", params,problems, transformers, DataItem.Type.MESSAGE);
		StreamScriptContext ssc = new StreamScriptContext(tenant, service,deployment);
		rs.execute(ssc, Optional.empty()).blockingForEach(item->{
			System.err.println("Item: "+item.message().toDataMap());
		});
		System.err.println("BLEEEEEE");
		
	}
}
