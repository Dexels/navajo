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
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class Delete implements ReactiveMerger, ParameterValidator {

	public Delete() {
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> execute(String relativePath, Optional<XMLElement> xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return context->(item,param)->{
			ReactiveResolvedParameters resolved = r.resolveNamed(context, Optional.of(item.message()),param.map(e->e.message()), this, xml, relativePath);
			String name = resolved.paramString("name");	
			return DataItem.of(item.message().without(Arrays.asList(name.split(","))));
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
