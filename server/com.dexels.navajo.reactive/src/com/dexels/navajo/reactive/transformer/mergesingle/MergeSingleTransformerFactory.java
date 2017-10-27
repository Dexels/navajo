package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

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
	public ReactiveTransformer build(XMLElement xml,Function<String,ReactiveSourceFactory> sourceSupplier, Function<String,ReactiveTransformerFactory> factorySupplier, Function<String,ReactiveMapper> mapperSupplier) {
//		, Optional<Function<StreamScriptContext, BiFunction<ReplicationMessage, ReplicationMessage, ReplicationMessage>>> reducer) {
		XMLElement reduceElement = xml.getElementByTagName("reducer");
//		if(reduceElement==null) {
//			throw new RuntimeException("Missing reduce element for xml: "+xml);
//		}
		XMLElement joinerElement = xml.getElementByTagName("joiner");
		if(joinerElement==null) {
			throw new RuntimeException("Missing joiner element for xml: "+xml);
		}
		Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> reducermapper = reduceElement==null? Optional.empty() : Optional.of(ReactiveScriptParser.parseMapperList(reduceElement.getChildren(), mapperSupplier));
		Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> joinermapper = ReactiveScriptParser.parseMapperList(joinerElement.getChildren(), mapperSupplier);
		Optional<ReactiveSource> subSource;
		try {
			subSource = ReactiveScriptParser.findSubSource(xml, sourceSupplier, factorySupplier,mapperSupplier);
		} catch (Exception e) {
			logger.error("Error: ", e);
			subSource = Optional.empty();
		}
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(xml);

		return new MergeSingleTransformer(parameters, subSource.get(), reducermapper,joinermapper);
	}
}
