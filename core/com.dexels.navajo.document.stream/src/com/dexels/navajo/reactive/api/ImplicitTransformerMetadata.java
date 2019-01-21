package com.dexels.navajo.reactive.api;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem.Type;

public class ImplicitTransformerMetadata implements TransformerMetadata {

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.empty();
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.empty();
		}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		return Optional.empty();
			}

	@Override
	public Set<Type> inType() {
		Set<Type> types = new HashSet<>();
		types.add(Type.MESSAGE);
		return types;
		
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public String name() {
		return "implicit";
	}

}
