package com.dexels.navajo.reactive.mappers;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessageParser;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class JsonFileAppender implements ReactiveMerger {

	public JsonFileAppender() {
	}

	@Override
	public Function<StreamScriptContext, Function<DataItem, DataItem>> execute(ReactiveParameters params) {
		ImmutableMessageParser parser = ImmutableFactory.createParser();
		return context -> {
			
			return (item) -> {
				ReactiveResolvedParameters named = params.resolve(context,Optional.of(item.message()), item.stateMessage(), this);
				String  path = named.paramString("path");
				boolean condition = named.optionalBoolean("condition").orElse(true);
				if(!condition) {
					return item;
				}
				
				FileOutputStream fw = new FileOutputStream(path,true);
				// TODO Fix
				byte[] data = parser.serialize(item.message());
				fw.write(data);
				fw.write("\n".getBytes(Charset.forName("UTF-8")));
				fw.close();
				return item;
			};
		};
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"path","condition"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("path", Property.STRING_PROPERTY);
		r.put("condition", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
