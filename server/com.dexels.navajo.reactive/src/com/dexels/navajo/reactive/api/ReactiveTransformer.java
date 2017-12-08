package com.dexels.navajo.reactive.api;

import java.util.Set;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.FlowableTransformer;

public interface ReactiveTransformer {
	public FlowableTransformer<DataItem,DataItem> execute(StreamScriptContext context);
	public Set<Type> inType();
	public DataItem.Type outType();
}
