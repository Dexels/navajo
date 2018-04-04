package com.dexels.navajo.reactive.api;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.functions.Function;


public interface ReactiveMerger extends ParameterValidator {
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml);
	default public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params) {
		return execute(params, "", Optional.empty());
	}
}
