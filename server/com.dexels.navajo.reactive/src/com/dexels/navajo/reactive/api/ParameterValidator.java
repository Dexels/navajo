package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParameterValidator {
	public Optional<List<String>> allowedParameters();
	public Optional<List<String>> requiredParameters();
	public Optional<Map<String,String>> parameterTypes();

}
