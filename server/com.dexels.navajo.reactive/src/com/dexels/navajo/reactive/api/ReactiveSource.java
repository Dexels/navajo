package com.dexels.navajo.reactive.api;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public interface ReactiveSource {
	public Flowable<DataItem> execute(StreamScriptContext context,Optional<ImmutableMessage> current);
	public DataItem.Type finalType();
	
	public final Function<String, ReactiveMerger> emptyReducerSupplier = (e->null);
}
