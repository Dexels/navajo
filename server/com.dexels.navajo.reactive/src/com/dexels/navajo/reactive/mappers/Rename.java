package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class Rename implements ReactiveMerger {

	public Rename() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params) {
		return context->(item)->{
			ReactiveResolvedParameters parms = params.resolve(context, Optional.of(item.message()), item.stateMessage(), this);
			boolean condition = parms.optionalBoolean("condition").orElse(true);
			if(!condition) {
				return item;
			}
			String fromKey = parms.paramString("from");
			Object oldValue = item.message().columnValue(fromKey);
			String oldType = item.message().columnType(fromKey);
			
			return DataItem.of(item.message().without(fromKey ).with(parms.paramString("to"),oldValue, oldType));
		};
	
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"to","from","condition"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"to","from"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("to", Property.STRING_PROPERTY);
		r.put("from", Property.STRING_PROPERTY);
		r.put("condition", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
