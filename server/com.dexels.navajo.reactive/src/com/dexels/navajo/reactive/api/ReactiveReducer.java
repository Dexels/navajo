package com.dexels.navajo.reactive.api;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public interface ReactiveReducer {
	public Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> execute(String relativePath, XMLElement xml);
}
