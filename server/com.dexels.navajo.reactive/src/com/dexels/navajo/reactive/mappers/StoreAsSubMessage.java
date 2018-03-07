package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class StoreAsSubMessage implements ReactiveMerger, ParameterValidator {

	public StoreAsSubMessage() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		return context->(item)->{
			// will use the second message as input, if not present, will use the source message
			ImmutableMessage s = item.message();
			ReactiveResolvedParameters parms = params.resolveNamed(context, Optional.of(s), item.stateMessage(), this, xml, relativePath);
			String name = parms.paramString("name");
			ImmutableMessage state = item.stateMessage().orElse(ImmutableFactory.empty());
			ImmutableMessage assembled = item.message().withSubMessage(name, state);
			return DataItem.of(assembled, item.stateMessage());
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
		Map<String,String> result = new HashMap<String, String>();
		result.put("name", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(result));
	}
}
