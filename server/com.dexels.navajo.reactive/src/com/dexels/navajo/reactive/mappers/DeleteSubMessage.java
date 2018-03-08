package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

public class DeleteSubMessage implements ReactiveMerger, ParameterValidator {

	public DeleteSubMessage() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		return context->(item)->{
			ReactiveResolvedParameters resolved = params.resolveNamed(context, Optional.of(item.message()),item.stateMessage(), this, xml, relativePath);
			String name = resolved.paramString("name");	
			// both singular and array submessages will be removed
			// TODO this could be more efficient
			return DataItem.of(item.message().withoutSubMessages(name).withoutSubMessage(name));
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
