package com.dexels.navajo.reactive.source.topology;

import java.util.Optional;
import java.util.Stack;

import org.apache.kafka.streams.Topology;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.kafka.streams.remotejoin.ReplicationTopologyParser;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.compiled.topology.TopologyTransformer;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class GroupTransformer implements ReactiveTransformer,TopologyTransformer {

	private TransformerMetadata metadata;
	private ReactiveParameters parameters;

	public GroupTransformer(TransformerMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.parameters = params;
	}
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
			Optional<ImmutableMessage> current, ImmutableMessage param) {
		return item->Flowable.error(()->new ReactiveParseException("Group transformer shouldn't be executed"));
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
	
	@Override
	public ReactiveParameters parameters() {
		return parameters;
	}
	@Override
	public Topology addTransformerToTopology(Topology topology, Stack<String> pipeStack) {
		ReplicationTopologyParser.addGroupedProcessor(topology, topologyContext, topologyConstructor, name, from, ignoreOriginalKey, key, ()->null);
	}


}
