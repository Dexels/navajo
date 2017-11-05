package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveReducer;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class MergeSingleTransformerFactory implements ReactiveTransformerFactory {

	
	private final static Logger logger = LoggerFactory.getLogger(MergeSingleTransformerFactory.class);

	public MergeSingleTransformerFactory() {
	}
	
	public void activate() {
		System.err.println("Activate mergesingle");
	}

	@Override
	public ReactiveTransformer build(String relativePath, XMLElement xml, Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveReducer> reducerSupplier, Function<String, ReactiveMapper> mapperSupplier) {
//		, Optional<Function<StreamScriptContext, BiFunction<ReplicationMessage, ReplicationMessage, ReplicationMessage>>> reducer) {
		XMLElement reduceElement = xml.getChildByTagName("reducer");
		XMLElement joinerElement = xml.getChildByTagName("joiner");
		if(joinerElement==null) {
			throw new RuntimeException("Missing joiner element for xml: "+xml);
		}
		Optional<Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>>> reducermapper = reduceElement==null? Optional.empty() : Optional.of(ReactiveScriptParser.parseReducerList(relativePath, reduceElement.getChildren(), reducerSupplier));
		Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath, joinerElement.getChildren(), reducerSupplier);
		Optional<ReactiveSource> subSource;
		try {
			System.err.println("looking for sub source: "+xml);
			subSource = ReactiveScriptParser.findSubSource(relativePath, xml, sourceSupplier, factorySupplier,reducerSupplier, mapperSupplier);
		} catch (Exception e) {
			logger.error("Error: ", e);
			subSource = Optional.empty();
		}
		if(!subSource.isPresent()) {
			throw new NullPointerException("Missing sub source in xml: "+xml);
		}
		return new MergeSingleTransformer(subSource.get(), reducermapper,joinermapper);
//		public MergeSingleTransformer(ReactiveSource source,  Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> reducer, Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> joiner) {

	}

}
