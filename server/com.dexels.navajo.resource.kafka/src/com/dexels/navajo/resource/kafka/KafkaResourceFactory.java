package com.dexels.navajo.resource.kafka;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.kafka.api.KafkaPublisher;
import com.dexels.kafka.api.KafkaSubscriber;

public class KafkaResourceFactory {

	private static KafkaResourceFactory instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(KafkaResourceFactory.class);
	
	private final Map<String,KafkaPublisher> kafkaPublishers = new HashMap<String, KafkaPublisher>();
	private final Map<String,KafkaSubscriber> kafkaSubscribers = new HashMap<String, KafkaSubscriber>();

	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}
	
	public static KafkaResourceFactory getInstance() {
		return instance;
	}
	
	public void addKafkaPublisher(KafkaPublisher resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not register kafka publisher: It has no name.");
			return;
		}
		kafkaPublishers.put(name, resource);
	}

	public void removeKafkaPublisher(KafkaPublisher resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not deregister http publisher: It has no name.");
			return;
		}
		kafkaPublishers.remove(name);
	}
	
	public KafkaPublisher getKafkaPublisher(String name) {
		return kafkaPublishers.get(name);
	}
	
	public void addKafkaSubscriber(KafkaSubscriber resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not register kafka subscriber: It has no name.");
			return;
		}
		kafkaSubscribers.put(name, resource);
	}

	public void removeKafkaSubscriber(KafkaSubscriber resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not deregister kafka subscriber: It has no name.");
			return;
		}
		kafkaSubscribers.remove(name);
	}
	
	public KafkaSubscriber getKafkaSubscriber(String name) {
		return kafkaSubscribers.get(name);
	}
}
