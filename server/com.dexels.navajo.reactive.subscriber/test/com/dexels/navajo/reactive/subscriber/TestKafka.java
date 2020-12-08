/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.kafka.factory.KafkaClientFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.kafka.SubscriberReactiveSourceFactory;
import com.dexels.navajo.reactive.pubsub.transformer.ReplicationMessageParseTransformer;
import com.dexels.navajo.reactive.pubsub.transformer.ReplicationMessageParseTransformerFactory;
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
		
		ReplicationMessageParseTransformerFactory transfac = new ReplicationMessageParseTransformerFactory();
//		public ReplicationMessageParseTransformer(TransformerMetadata metadata, ReplicationMessageParser parser, ReactiveParameters parameters) {
		
		ReactiveParameters parameters = ReactiveParameters.empty(transfac);
		ReactiveTransformer rt = new ReplicationMessageParseTransformer(transfac, parser,parameters);
		List<ReactiveTransformer> transformers = Arrays.asList(new ReactiveTransformer[]{rt});
		
		ReactiveParameters params = ReactiveParameters.empty(krsf)
				.withConstant("topic", "sometopic", "string")
//				.withConstant("group", "mygroup", "string")
//				.withConstant("from", "0:10", "string")
				.withConstant("to", "0:1000", "string")
				;

		ReactiveSource rs = krsf.build(params);
		StreamScriptContext ssc = new StreamScriptContext(tenant, service,deployment);
		rs.execute(ssc, Optional.empty(),ImmutableFactory.empty())
		
			.compose(rt.execute(ssc, Optional.empty(), ImmutableFactory.empty()))
			.blockingForEach(item->{
			System.err.println("Item: "+item.message().toDataMap());
		});
		
	}
}
