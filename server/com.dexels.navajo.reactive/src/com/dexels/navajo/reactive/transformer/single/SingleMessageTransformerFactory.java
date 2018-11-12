package com.dexels.navajo.reactive.transformer.single;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.functions.Function;

public class SingleMessageTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	public SingleMessageTransformerFactory() {
	}

	@Override
	public ReactiveTransformer build(Type parentType, String relativePath, List<ReactiveParseProblem> problems, ReactiveParameters parameters,Optional<XMLElement> xml, 
			ReactiveBuildContext buildContext) {

		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath, problems, xml.map(x->x.getChildren()),buildContext);
		return new SingleMessageTransformer(this,parameters,joinermapper,xml,relativePath);
	}


	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}


	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.empty();
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> res = new HashMap<>();
		res.put("debug", "boolean");
		return Optional.of(Collections.unmodifiableMap(res));
	}


	@Override
	public String name() {
		return "single";
	}
}
