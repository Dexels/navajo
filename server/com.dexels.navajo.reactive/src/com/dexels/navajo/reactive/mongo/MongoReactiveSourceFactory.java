package com.dexels.navajo.reactive.mongo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class MongoReactiveSourceFactory implements ReactiveSourceFactory {

	public MongoReactiveSourceFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ReactiveSource build(
			Map<String, BiFunction<StreamScriptContext, Optional<ReplicationMessage>, Operand>> namedParameters,
			List<BiFunction<StreamScriptContext, Optional<ReplicationMessage>, Operand>> unnamedParameters,
			List<Function<StreamScriptContext, FlowableTransformer<ReplicationMessage, ReplicationMessage>>> rest,
			String outputName, boolean isOutputArray
	) {
		return new MongoReactiveSource(namedParameters, unnamedParameters, rest,outputName,isOutputArray);
	}

}
