package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public interface ReactiveSourceFactory {
	public ReactiveSource build(
			Map<String, BiFunction<StreamScriptContext, Optional<ReplicationMessage>, Operand>> namedParameters,
			List<BiFunction<StreamScriptContext, Optional<ReplicationMessage>, Operand>> unnamedParameters,
			List<Function<StreamScriptContext, FlowableTransformer<ReplicationMessage, ReplicationMessage>>> rest,
			String outputName, boolean isOutputArray);

}
