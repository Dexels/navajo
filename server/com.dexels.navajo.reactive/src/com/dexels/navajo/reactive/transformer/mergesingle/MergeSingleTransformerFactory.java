package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class MergeSingleTransformerFactory implements ReactiveTransformerFactory {

	
	public MergeSingleTransformerFactory() {
	}
	
	public void activate() {
	}

	@Override
	public ReactiveTransformer build(String relativePath, Optional<XMLElement> xmlElement, Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier) {
//		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath, xmlElement);
		XMLElement xml = xmlElement.orElseThrow(()->new RuntimeException("MergeSingleTransformerFactory: Can't build without XML element"));

		
		XMLElement joinerElement = xml.getChildByTagName("joiner");
		if(joinerElement==null) {
			throw new RuntimeException("Missing joiner element for xml: "+xml);
		}
		Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath, Optional.of(joinerElement.getChildren()), reducerSupplier);
		Optional<ReactiveSource> subSource;
		try {
			subSource = ReactiveScriptParser.findSubSource(relativePath, xml, sourceSupplier, factorySupplier,reducerSupplier);
		} catch (Exception e) {
			throw new ReactiveParseException("Unable to parse sub source in xml: "+xml,e);
		}
		if(!subSource.isPresent()) {
			throw new NullPointerException("Missing sub source in xml: "+xml);
		}
		return new MergeSingleTransformer(subSource.get(), joinermapper,xml);
	}

}
