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
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveReducer;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class CopyMessageList implements ReactiveReducer, ParameterValidator {

	public CopyMessageList() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> execute(String relativePath, XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return context->(item,second)->{
			ReactiveResolvedParameters resolved = r.resolveNamed(context, Optional.of(item.message()),Optional.empty(), this,xml,relativePath);
			
			List<ReplicationMessage> l = second.msgList();
//			Map<String,Operand> named = r.resolveNamed(context, item, Optional.of(second));
			return DataItem.of(item.message().withSubMessages(resolved.paramString("name"), l));
		};
	
	}

	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"name"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"name"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("name", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
