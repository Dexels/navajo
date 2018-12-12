package com.dexels.navajo.reactive.pubsub.transformer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;

public class ReplicationMessageParseTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	FallbackReplicationMessageParser parser = new FallbackReplicationMessageParser(true);

	@Override
	public ReactiveTransformer build(Type parentType,List<ReactiveParseProblem> problems,ReactiveParameters parameters,
			ReactiveBuildContext buildContext) {
		return new ReplicationMessageParseTransformer(this, parser,parameters);
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.DATA})) ;
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[] {"source"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> types = new HashMap<>();
		types.put("source","string");
		return Optional.of(Collections.unmodifiableMap(types));
	}
	

	@Override
	public String name() {
		return "parse";
	}
}
