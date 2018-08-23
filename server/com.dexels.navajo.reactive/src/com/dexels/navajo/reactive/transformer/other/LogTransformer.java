package com.dexels.navajo.reactive.transformer.other;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveBuildContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class LogTransformer implements ReactiveTransformer {

	private TransformerMetadata metadata;
	private String relativePath;
	private ReactiveParameters parameters;
	private Optional<XMLElement> xml;
	private ReactiveBuildContext buildContext;

	
	private final static Logger logger = LoggerFactory.getLogger(LogTransformer.class);

	public LogTransformer(TransformerMetadata metadata, String relativePath, List<ReactiveParseProblem> problems, ReactiveParameters parameters, Optional<XMLElement> xml, ReactiveBuildContext buildContext) {
		this.metadata = metadata;
		this.relativePath = relativePath;
		this.parameters = parameters;
		this.xml = xml;
		this.buildContext = buildContext;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = this.parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, this.xml, this.relativePath);
		return flow->flow.doOnSubscribe(e->{
			logger.info("Subscribed stream at line: {}",this.xml.map(x->x.getStartLineNr()).orElse(-1));
		}
		).doOnNext(e->logger.info("Element at line: {} content: {}",this.xml.map(x->x.getStartLineNr()).orElse(-1),ImmutableFactory.getInstance().describe(e.message())))
		.doOnComplete(()->{
			logger.info("Completed stream at line: {}",this.xml.map(x->x.getStartLineNr()).orElse(-1));
		});
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public Optional<XMLElement> sourceElement() {
		return xml;
	}

}
