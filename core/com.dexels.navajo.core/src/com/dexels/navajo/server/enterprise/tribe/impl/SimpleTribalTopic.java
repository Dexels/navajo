package com.dexels.navajo.server.enterprise.tribe.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.server.enterprise.tribe.TopicListener;
import com.dexels.navajo.server.enterprise.tribe.TribalTopic;

public class SimpleTribalTopic implements TribalTopic {

	final String topicName;
	final Set<TopicListener> listeners = new HashSet<TopicListener>();
	int count;
	
	public SimpleTribalTopic(String s) {
		topicName = s;
	}
	
	@Override
	public void addTopicListener(TopicListener tl) {
		listeners.add(tl);
		count++;
	}

	@Override
	public void publish(Serializable s) {
		for ( TopicListener tl : listeners ) {
			tl.onTopic(new SimpleTopicEvent(s));
		}
	}

	@Override
	public void removeTopicListener(TopicListener tl) {
		listeners.remove(tl);
		count--;
	}

	@Override
	public String getName() {
		return topicName;
	}

	@Override
	public long getInterestCount() {
		return count;
	}

}
