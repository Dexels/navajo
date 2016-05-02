package com.dexels.navajo.resource.kafka;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.kafka.api.KafkaPublisher;
import com.dexels.kafka.factory.KafkaClientFactory;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestKafkaResource {

	@Before
	public void setUp() throws Exception {
		KafkaResourceFactory factory = new KafkaResourceFactory();
		
	}

	@After
	public void tearDown() throws Exception {
//		KafkaResourceFactory.getInstance().deactivate();	
	}

	@Test
	public void testSimple() throws MappableException, UserException, InterruptedException {
		KafkaPublisher kp = KafkaClientFactory.createPublisher("cerberus1.sportlink-infra.net:9092,cerberus2.sportlink-infra.net:9094");
		kp.publish("my-topic2", "key", "blubblub".getBytes());
		Thread.currentThread().sleep(1000);
	}
}
