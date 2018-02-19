package com.dexels.navajo.reactive.kafka;

import java.util.List;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.pubsub.rx2.api.TopicSubscriber;

import io.reactivex.functions.Function;

public class SubscriberReactiveSourceFactory implements ReactiveSourceFactory {

	public SubscriberReactiveSourceFactory() {}


	private TopicSubscriber topicSubscriber;
	

    public void setTopicSubscriber(TopicSubscriber topicSubscriber) {
        this.topicSubscriber = topicSubscriber;
    }

    public void clearTopicSubscriber(TopicSubscriber topicSubscriber) {
        this.topicSubscriber = null;
    }

	
	@Override
	public ReactiveSource build(String relativePath, String type, XMLElement x, ReactiveParameters params,
			List<ReactiveTransformer> transformers, Type finalType, Function<String, ReactiveMerger> reducerSupplier,
			Function<String, ReactiveMapper> mapperSupplier) {

		return new SubscriberReactiveSource(topicSubscriber,params,relativePath,x,finalType,transformers,reducerSupplier,mapperSupplier);

	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}

}
