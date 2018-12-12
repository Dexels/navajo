package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Arrays;
import java.util.Collections;
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
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.functions.Function;

public class MergeSingleTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	
	public MergeSingleTransformerFactory() {
	}
	
	public void activate() {
	}

	@Override
	public ReactiveTransformer build(Type parentType, List<ReactiveParseProblem> problems,
			ReactiveParameters parameters, ReactiveBuildContext buildContext) {
//		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("MergeSingleTransformerFactory: Can't build without XML element"));
//		parameters.unnamed.stream().findFirst()
//		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), buildContext);

//		if(!sub.finalType().equals(DataItem.Type.SINGLEMESSAGE)) {
//			throw new IllegalArgumentException("Wrong type of sub source: "+sub.finalType()+ ", reduce or first maybe? It should be: "+Type.SINGLEMESSAGE+" at line: "+xml.getStartLineNr()+" xml: \n"+xml);
//		}
		// TODO
		return new MergeSingleTransformer(this,parameters,context->item->item);
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
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		return Optional.of(Collections.emptyMap());
	}

	@Override
	public String name() {
		return "mergesingle";
	}

}
