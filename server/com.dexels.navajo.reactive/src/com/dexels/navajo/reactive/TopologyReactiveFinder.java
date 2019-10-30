package com.dexels.navajo.reactive;

import com.dexels.navajo.reactive.source.topology.FilterTransformerFactory;
import com.dexels.navajo.reactive.source.topology.GroupTransformerFactory;
import com.dexels.navajo.reactive.source.topology.SinkTransformerFactory;
import com.dexels.navajo.reactive.source.topology.TopicSourceFactory;

public class TopologyReactiveFinder extends CoreReactiveFinder {

	public TopologyReactiveFinder() {
		addReactiveSourceFactory(new TopicSourceFactory(),"topic");
		addReactiveTransformerFactory(new GroupTransformerFactory(),"group");
		addReactiveTransformerFactory(new SinkTransformerFactory(),"sink");
		addReactiveTransformerFactory(new FilterTransformerFactory(),"filter");
	}

}
