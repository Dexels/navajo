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
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class Rename implements ReactiveMapper, ParameterValidator {

	public Rename() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(String relativePath, XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return context->(item)->{
			ReactiveResolvedParameters parms = r.resolveNamed(context, Optional.of(item.message()), Optional.empty(), this, xml, relativePath);
//			Map<String,Operand> named = r.resolveNamed(context, item,Optional.empty());
//			Operand value = named.get("value");
//			String to = (String)named.get("to").value;
			String fromKey = parms.paramString("from");
			Object oldValue = item.message().columnValue(fromKey);
			String oldType = item.message().columnType(fromKey);
			DataItem result = DataItem.of(item.message().without(fromKey ).with(parms.paramString("to"),oldValue, oldType));
//			String ss = ReplicationFactory.getInstance().describe(result.message());
			return result;
		};
	
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"to","from"}));
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
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
