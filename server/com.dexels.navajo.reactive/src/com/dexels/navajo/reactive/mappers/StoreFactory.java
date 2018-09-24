package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

public class StoreFactory implements ReactiveTransformerFactory {

	public StoreFactory() {
	}

	// TODO fix, now broken. Do we need it?
	@Override
	public ReactiveTransformer build(Type parentType, String relativePath, List<ReactiveParseProblem> problems, ReactiveParameters parameters, Optional<XMLElement> xml,
			ReactiveBuildContext buildContext) {

		return null; //new Store();
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","value"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","value"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> result = new HashMap<String, String>();
		result.put("name", Property.STRING_PROPERTY);
		result.put("value", Property.INTEGER_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(result));
	}

	@Override
	public Set<Type> inType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type outType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		return "store";
	}

}
