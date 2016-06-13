package com.dexels.navajo.resource.kafka;

import org.junit.After;
import org.junit.Test;

import com.dexels.kafka.factory.KafkaClientFactory;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.pubsub.api.MessagePublisher;

public class TestKafkaResource {


	@After
	public void tearDown() throws Exception {
//		KafkaResourceFactory.getInstance().deactivate();	
	}

	@Test
	public void testSimple() throws MappableException, UserException, InterruptedException {
		MessagePublisher kp = KafkaClientFactory.createPublisher("cerberus1.sportlink-infra.net:9092,cerberus2.sportlink-infra.net:9094","topic");
		kp.publish("key", "blubblub".getBytes());
		Thread.sleep(1000);
	}
}
