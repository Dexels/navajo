package com.dexels.navajo.reactive.api;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;


public interface ReactiveMerger {
	public Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> execute(String relativePath, Optional<XMLElement> xml);
}
