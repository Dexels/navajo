package com.dexels.navajo.reactive.api;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.FlowableTransformer;

public interface ReactiveTransformer {

	public FlowableTransformer<DataItem,DataItem> execute(StreamScriptContext context);
	public TransformerMetadata metadata();
}
