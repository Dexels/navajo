package com.dexels.navajo.document.stream;

import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;

public interface ReactiveScript {
	public Flowable<DataItem> execute(StreamScriptContext context);
	public DataItem.Type dataType();
}
