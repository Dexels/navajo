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
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class JsonFileAppender implements ReactiveMapper, ParameterValidator {

	public JsonFileAppender() {
	}

	@Override
	public Function<StreamScriptContext, Function<DataItem, DataItem>> execute(String relativePath, XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		ImmutableMessageParser parser = ImmutableFactory.createParser();
		return context -> {
			ReactiveResolvedParameters named = r.resolveNamed(context, Optional.empty(), Optional.empty(), this,xml,relativePath);
			String  path = named.paramString("path");
			return (item) -> {
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
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("path", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
