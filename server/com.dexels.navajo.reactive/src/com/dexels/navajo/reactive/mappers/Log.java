package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessageParser;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class Log implements ReactiveMerger {

	
	private final static Logger logger = LoggerFactory.getLogger(Log.class);

	public Log() {
	}

	@Override
	public Function<StreamScriptContext, Function<DataItem, DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		ImmutableMessageParser parser = ImmutableFactory.createParser();
		return context -> {
			
			return (item) -> {
				ReactiveResolvedParameters named = params.resolveNamed(context,Optional.of(item.message()), item.stateMessage(), this,xml,relativePath);
				boolean condition = named.optionalBoolean("condition").orElse(true);
				if(!condition) {
					return item;
				}
				
				byte[] serialized = parser.serialize(item.message());
				
				String data = serialized ==null? "<empty>" : new String(serialized);
				logger.info(data);
				return item;
			};
		};
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"condition"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("condition", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}