package com.dexels.navajo.reactive.subscriber;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import com.dexels.kafka.factory.KafkaClientFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.kafka.SubscriberReactiveSourceFactory;
import com.dexels.pubsub.rx2.api.TopicPublisher;
import com.dexels.pubsub.rx2.api.TopicSubscriber;

public class TestKafka {

	public TestKafka() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testKafka() {
		TopicSubscriber subscriber = KafkaClientFactory.createSubscriber("10.0.0.7:9092", Collections.emptyMap());
		SubscriberReactiveSourceFactory krsf = new SubscriberReactiveSourceFactory();
		krsf.setTopicSubscriber(subscriber);
		
//		ReactiveSource rs = krsf.build("", DataItem.Type.MESSAGE, x, params, transformers, finalType, reducerSupplier, mapperSupplier);
//		StreamScriptContext ssc = new StreamScriptContext(tenant, service)
//		rs.execute(context, Optional.empty());
	}
}
