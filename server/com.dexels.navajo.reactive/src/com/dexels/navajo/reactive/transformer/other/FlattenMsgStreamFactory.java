package com.dexels.navajo.reactive.transformer.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class FlattenMsgStreamFactory implements ReactiveTransformerFactory, TransformerMetadata {
	
	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems,
			ReactiveParameters parameters) {
//		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("MergeSingleTransformerFactory: Can't build without XML element"));
//		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), buildContext);

		return new FlattenMsgStream(this,parameters);
	}
	

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.MSGSTREAM}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[] {"parallel","inOrder"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String, String> r = new HashMap<>();
//		r.put("isArrayElement", Property.BOOLEAN_PROPERTY);
		r.put("inOrder", Property.BOOLEAN_PROPERTY);
		r.put("parallel", Property.INTEGER_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
	

	@Override
	public String name() {
		return "flattenmsg";
	}



}
