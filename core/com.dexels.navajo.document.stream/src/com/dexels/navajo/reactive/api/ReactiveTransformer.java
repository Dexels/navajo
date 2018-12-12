package com.dexels.navajo.reactive.api;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.FlowableTransformer;

public interface ReactiveTransformer {

	public FlowableTransformer<DataItem,DataItem> execute(StreamScriptContext context,Optional<ImmutableMessage> current, ImmutableMessage param);
	public TransformerMetadata metadata();

}
