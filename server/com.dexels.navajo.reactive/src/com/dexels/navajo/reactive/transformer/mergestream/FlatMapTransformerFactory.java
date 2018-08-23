package com.dexels.navajo.reactive.transformer.mergestream;

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
import com.dexels.navajo.reactive.ReactiveBuildContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.TransformerMetadata;

public class FlatMapTransformerFactory implements ReactiveTransformerFactory, TransformerMetadata {

	
	private ReactiveSource childSource = null;

	public FlatMapTransformerFactory() {
	}
	
	public void activate() {
	}

	@Override
	public ReactiveTransformer build(String relativePath, List<ReactiveParseProblem> problems, ReactiveParameters parameters, 
			Optional<XMLElement> xmlElement,
			ReactiveBuildContext buildContext) {

		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("MergeMultiTransformerFactory: Can't build without XML element"));
//		Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath,problems, Optional.of(xml.getChildren()), buildContext);
		Optional<ReactiveSource> subSource;
		try {
			subSource = ReactiveScriptParser.findSubSource(relativePath, xml, problems, buildContext);
		} catch (Exception e) {
			throw new ReactiveParseException("Unable to parse sub source in xml: "+xml,e);
		}
		if(!subSource.isPresent()) {
			throw new NullPointerException("Missing sub source in xml: "+xml);
		}
		childSource = subSource.get();
//		if(!childSource.finalType().equals(DataItem.Type.MESSAGE)) {
//			throw new IllegalArgumentException("Wrong type of sub source: "+childSource.finalType()+ ", reduce or first maybe? It should be: "+Type.SINGLEMESSAGE+" at line: "+xml.getStartLineNr()+" xml: \n"+xml);
//		}
		return new FlatMapTransformer(this,parameters,childSource,xmlElement);
	}
	

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}


	@Override
	public Type outType() {
		return childSource.finalType();
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
		return "flatmap";
	}


}
