package com.dexels.navajo.reactive.api;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.functions.Function;

public interface ReactiveMapper {
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(String relativePath,XMLElement xml);
}
