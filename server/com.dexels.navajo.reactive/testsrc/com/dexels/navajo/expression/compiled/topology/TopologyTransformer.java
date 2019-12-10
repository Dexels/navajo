package com.dexels.navajo.expression.compiled.topology;

import java.util.Stack;

import org.apache.kafka.streams.Topology;

public interface TopologyTransformer {
	public Topology addTransformerToTopology(Topology topology, Stack<String> pipeStack);
}
